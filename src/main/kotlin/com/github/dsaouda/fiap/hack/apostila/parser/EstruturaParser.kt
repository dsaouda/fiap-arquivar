package com.github.dsaouda.fiap.hack.apostila.parser

import com.github.dsaouda.fiap.hack.apostila.page.ArquivosPage
import com.github.dsaouda.fiap.hack.common.basePath
import org.jsoup.select.Elements
import java.io.File

class EstruturaParser(private val elements: Elements) {

    fun execute() {

        elements.forEach({ e ->

            val span = e.select("span")
            val parametrosUrl = span.attr("onclick")

            val regex = Regex("^.*?,'.*?','(.*?)'.*\$")
            val post = regex.find(parametrosUrl)?.groups?.get(1)?.value?.split("&")

            val disciplina = span.select(".i-apostilas-label-title").text()
            val professor = span.select(".i-apostilas-label-subtitle").text()

            val diretorioDisciplina = File(basePath("/${disciplina}"))
            if (!diretorioDisciplina.exists()) diretorioDisciplina.mkdir()

            val readme = File(basePath("/${disciplina}/README.md"))
            if (!readme.exists()) {
                readme.writeText("Professor: ${professor}")
            }

            println("\n")
            println("Disciplina: ${disciplina}")
            println("Professor: ${professor}")

            val arquivos = ArquivosPage.request(post)
            ArquivosParser(arquivos, diretorioDisciplina).process()
        })

    }

}