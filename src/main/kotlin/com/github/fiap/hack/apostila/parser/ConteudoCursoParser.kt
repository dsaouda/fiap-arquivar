package com.github.fiap.hack.apostila.parser

import com.github.fiap.hack.apostila.*
import com.github.fiap.hack.apostila.page.ArquivoPastaPage
import com.github.fiap.hack.apostila.page.DownloadPage
import org.jsoup.nodes.Element
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ConteudoCursoParser(private val span: Element, private val diretorioDisciplina: File) {

    fun process(cookie: String) {
        val spanApostila = span.select(".i-apostilas-label")
        val spanArquivo =  span.select(".i-apostilas-link")

        if (spanApostila.size > 0) {

            val paramestrosPasta = spanApostila.attr("onclick")

            val pasta = spanApostila.select(".i-apostilas-label-title").text()

            val regex = Regex("^.*?'','(.*?)'")
            val post = regex.find(paramestrosPasta)?.groups?.get(1)?.value?.split("&")
            val arquivos = ArquivoPastaPage().request(post, cookie)
            val file = File("${diretorioDisciplina.absolutePath}/${pasta}")
            if (!file.exists()) {
                file.mkdirs()
            }

            ArquivosParser(arquivos, file).process(cookie)
        }

        if (spanArquivo.size > 0) {
            val paramDownlaod = spanArquivo.attr("onclick")

            when {
                paramDownlaod.trim().length == 0 -> {
                    val a = spanArquivo.select("a")
                    val link = a.attr("href")
                    val arquivo = a.text()
                    val data = extrairData(span.select(".i-apostilas-link-subtitle").text())

                    println("download downloadLink ${arquivo} na data ${data}")

                    val file = File("${diretorioDisciplina.absolutePath}/${data} - ${arquivo}")
                    if (!file.exists()) {
                        val conteudo = DownloadPage(cookie).downloadLink(link)
                        file.writeBytes(conteudo)
                    }
                }

                else -> {
                    val arquivo = span.select(".i-apostilas-link-title").text()
                    val data = extrairData(span.select(".i-apostilas-link-subtitle").text())
                    DownloadPage(cookie).download(diretorioDisciplina, paramDownlaod, arquivo, data)
                }
            }
        }
    }

    private fun extrairData(text: String): LocalDate {
        val data = text.replace(Regex(".*\\W\\s"), "")

        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        return LocalDate.parse(data, formatter)
    }

}