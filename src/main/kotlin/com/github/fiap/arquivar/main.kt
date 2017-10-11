package com.github.fiap.arquivar

import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.system.exitProcess

private var cookie: String? = null

fun main(args: Array<String>) {
    cookie = args[0].trim()

    val parse = requestEstrutura()
    val elements = parse.select("#curso185_2016turma28SCJ_2016all .i-apostilas-item")

    elements.forEach({ e ->

        val span = e.select("span")
        val parametrosUrl = span.attr("onclick")

        val regex = Regex("^.*?,'.*?','(.*?)'.*\$")
        val post = regex.find(parametrosUrl)?.groups?.get(1)?.value?.split("&")

        val disciplina = span.select(".i-apostilas-label-title").text()
        val professor = span.select(".i-apostilas-label-subtitle").text()

        val diretorioDisciplina = File("data/${disciplina}")
        if (!diretorioDisciplina.exists()) diretorioDisciplina.mkdir()

        val readme = File("data/${disciplina}/README.md")
        if (!readme.exists()) {
            readme.writeText("Professor: ${professor}")
        }

        if (!disciplina.equals("Arquitetura e Desenvolvimento Java de Alta Disponibilidade para Cloud Computing")) {
            //return@forEach
        }

        println("\n")
        println("Disciplina: ${disciplina}")
        println("Professor: ${professor}")

        val arquivos = requestArquivo(post)
        processarArquivos(arquivos, diretorioDisciplina)

    })
}

private fun processarArquivos(arquivos: Document, diretorioDisciplina: File) {
    arquivos.select("span[onclick]").forEach({ span ->
        estrutura(span, diretorioDisciplina)
    })
}

private fun estrutura(span: Element, diretorioDisciplina: File) {
    val spanApostila = span.select(".i-apostilas-label")
    val spanArquivo =  span.select(".i-apostilas-link")

    if (spanApostila.size > 0) {

        val paramestrosPasta = spanApostila.attr("onclick")

        val pasta = spanApostila.select(".i-apostilas-label-title").text()

        val regex = Regex("^.*?'','(.*?)'")
        val post = regex.find(paramestrosPasta)?.groups?.get(1)?.value?.split("&")
        val arquivos = requestArquivoPasta(post)
        val file = File("${diretorioDisciplina.absolutePath}/${pasta}")
        if (!file.exists()) {
            file.mkdirs()
        }

        processarArquivos(arquivos, file)
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
                    val conteudo = downloadLink(link)
                    file.writeBytes(conteudo)
                }
            }

            else -> {
                val arquivo = span.select(".i-apostilas-link-title").text()
                val data = extrairData(span.select(".i-apostilas-link-subtitle").text())
                download(diretorioDisciplina, paramDownlaod, arquivo, data)
            }
        }
    }
}

private fun download(diretorioDisciplina: File, paramDownlaod: String, arquivo: String, data: LocalDate) {

    when {
        paramDownlaod.indexOf("fDownloadArquivo") == 0 -> {
            println("download fDownloadArquivo ${arquivo} na data ${data}")
            val codigo = paramDownlaod.replace(Regex("fDownloadArquivo|\\(|\\)|'|;"), "")
            val file = File("${diretorioDisciplina.absolutePath}/${data} - ${arquivo}")
            if (!file.exists()) {
                val conteudo = downloadArquivo(codigo)
                file.writeBytes(conteudo)
            }

            return
        }

        paramDownlaod.indexOf("fDownload") == 0 -> {
            println("download fDownload ${arquivo} na data ${data}")

            val codigo = paramDownlaod.replace(Regex("fDownload|\\(|\\)|'|;"), "")
            val codigos = codigo.split(",")

            val file = File("${diretorioDisciplina.absolutePath}/${data} - ${arquivo}")
            if (!file.exists()) {
                val conteudo = fDownload(codigos[0], codigos[1])
                file.writeBytes(conteudo)
            }

            return
        }
    }
}

private fun downloadArquivo(codigo: String): ByteArray {
    //<form name="frmDownload" id="frmDownload" method="post" action="login/alunos_2004/apostilas_2007/DownloadOutraExtensao.asp" target="iFrameAcao">
    //<input type="hidden" name="Apostila" id="Apostila" />
    //</form>

    val conn = Jsoup.connect("https://www2.fiap.com.br/programas/login/alunos_2004/apostilas_2007/DownloadOutraExtensao.asp")
            .method(Connection.Method.POST)
            .referrer("https://www2.fiap.com.br/programas/login/alunos_2004/apostilas_2007/default.asp?titulo_secao=Apostilas")
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
            .ignoreContentType(true)
            .maxBodySize(0)
            .header("Host", "www2.fiap.com.br")
            .header("Origin", "https://www2.fiap.com.br")
            .header("Cookie", cookie)
            .header("Upgrade-Insecure-Requests", "1")
            .data("Apostila", codigo)

    return conn.execute().bodyAsBytes()
}

private fun downloadLink(link: String): ByteArray {

    val conn = Jsoup.connect("https://www2.fiap.com.br/programas/${link}")
            .method(Connection.Method.GET)
            .referrer("https://www2.fiap.com.br/programas/login/alunos_2004/apostilas_2007/default.asp?titulo_secao=Apostilas")
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
            .ignoreContentType(true)
            .maxBodySize(0)
            .header("Host", "www2.fiap.com.br")
            .header("Origin", "https://www2.fiap.com.br")
            .header("Cookie", cookie)
            .header("Upgrade-Insecure-Requests", "1");

    return conn.execute().bodyAsBytes()
}

private fun fDownload(codigoA: String, codigoB: String): ByteArray {
    //<form action="./login/alunos_2004/apostilas_2007/download.asp" name="frm_download" id="frm_download" method="post">
    //<input type="hidden" name="a" id="a" value="">
    //<input type="hidden" name="c" id="c" value="">
    //</form>

    val conn = Jsoup.connect("https://www2.fiap.com.br/programas/login/alunos_2004/apostilas_2007/download.asp")
            .method(Connection.Method.POST)
            .referrer("https://www2.fiap.com.br/programas/login/alunos_2004/apostilas_2007/default.asp?titulo_secao=Apostilas")
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
            .ignoreContentType(true)
            .maxBodySize(0)
            .header("Host", "www2.fiap.com.br")
            .header("Origin", "https://www2.fiap.com.br")
            .header("Cookie", cookie)
            .header("Upgrade-Insecure-Requests", "1")
            .data("a", codigoA)
            .data("c", codigoB)

    return conn.execute().bodyAsBytes()
}

private fun extrairData(text: String): LocalDate {
    val data = text.replace(Regex(".*\\W\\s"), "")

    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    return LocalDate.parse(data, formatter)
}

private fun requestArquivoPasta(data: List<String>?): Document {
    val conn = Jsoup.connect("https://www2.fiap.com.br/programas/login/alunos_2004/apostilas_2007/_arquivosPasta.asp")
            .method(Connection.Method.POST)
            .referrer("https://www2.fiap.com.br/programas/login/alunos_2004/apostilas_2007/default.asp?titulo_secao=Apostilas")
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
            .header("X-Requested-With", "XMLHttpRequest")
            .header("X-Prototype-Version", "1.4.0")
            .header("Host", "www2.fiap.com.br")
            .header("Origin", "https://www2.fiap.com.br")
            .header("Cookie", cookie)

    data?.forEach { d ->
        val valor = d.split("=")
        conn.data(valor[0], valor[1])
    }

    return conn.execute().parse()
}


private fun requestArquivo(data: List<String>?): Document {
    val conn = Jsoup.connect("https://www2.fiap.com.br/programas/login/alunos_2004/apostilas_2007/_arquivos.asp")
            .method(Connection.Method.POST)
            .referrer("https://www2.fiap.com.br/programas/login/alunos_2004/apostilas_2007/default.asp?titulo_secao=Apostilas")
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
            .header("X-Requested-With", "XMLHttpRequest")
            .header("X-Prototype-Version", "1.4.0")
            .header("Host", "www2.fiap.com.br")
            .header("Origin", "https://www2.fiap.com.br")
            .header("Cookie", cookie)

    data?.forEach { d ->
        val valor = d.split("=")
        conn.data(valor[0], valor[1])
    }

    return conn.execute().parse()
}

private fun requestEstrutura(): Document = Jsoup.connect("https://www2.fiap.com.br/programas/login/alunos_2004/apostilas_2007/_estrutura.asp")
            .method(Connection.Method.POST)
            .referrer("https://www2.fiap.com.br/programas/login/alunos_2004/apostilas_2007/default.asp?titulo_secao=Apostilas")
            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
            .header("X-Requested-With", "XMLHttpRequest")
            .header("X-Prototype-Version", "1.4.0")
            .header("Host", "www2.fiap.com.br")
            .header("Origin", "https://www2.fiap.com.br")
            .header("Cookie", cookie)
            .execute()
            .parse()