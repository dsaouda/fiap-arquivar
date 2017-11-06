package com.github.dsaouda.fiap.hack.apostila.parser

import org.jsoup.nodes.Document
import java.io.File


class ArquivosParser (private val arquivos: Document, private val diretorioDisciplina: File) {

    fun process() {
        arquivos.select("span[onclick]").forEach({ span ->
            ConteudoCursoParser(span, diretorioDisciplina).process()
        })
    }
}