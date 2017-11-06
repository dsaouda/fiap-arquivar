package com.github.dsaouda.fiap.hack.trabalho.parser

import com.github.dsaouda.fiap.hack.trabalho.dto.VerTrabalhoDTO
import org.jsoup.Connection
import org.jsoup.nodes.Document

class VerTrabalhoParser {

    private val document: Document

    constructor(response: Connection.Response) {
        document = response.parse()
    }

    fun getDTO(): VerTrabalhoDTO {
        val href = document.select(".i-content-link").attr("href")
        return VerTrabalhoDTO(href)
    }
}