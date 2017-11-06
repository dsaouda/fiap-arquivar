package com.github.dsaouda.fiap.hack.trabalho

import com.github.dsaouda.fiap.hack.trabalho.page.TrabalhoPage

fun cli() {
    val parser = TrabalhoPage.lista()
    val trabalhos = parser.trabalhos()

    trabalhos.forEach { trabalho ->
        trabalho.createDirectory()
        trabalho.writeContentReadme()

        println(trabalho.readmeContent())

        val verTrabalhoParser = TrabalhoPage.verTrabalho(trabalho.codigoTrabalho)
        val detalheTrabalho = verTrabalhoParser.getDTO()

        val content = TrabalhoPage.download(detalheTrabalho.linkArquivo)

        if (detalheTrabalho.writeContentIfFileNotExists(trabalho.dirname(), content)) {
            println("Link arquivo: ${detalheTrabalho.linkArquivo}")
        }

        println("===============")
    }
}