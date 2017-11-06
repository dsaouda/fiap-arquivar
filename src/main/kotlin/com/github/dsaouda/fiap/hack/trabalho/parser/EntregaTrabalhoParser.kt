package com.github.dsaouda.fiap.hack.trabalho.parser

import com.github.dsaouda.fiap.hack.trabalho.dto.EntregaTrabalhoDTO
import com.github.dsaouda.fiap.hack.trabalho.extrator.TrabalhoExtractor
import org.jsoup.Connection.Response
import org.jsoup.nodes.Document

class EntregaTrabalhoParser {

    private val document: Document

    constructor(response: Response) {
        document = response.parse()
    }

    fun trabalhos(): List<EntregaTrabalhoDTO> {
        val elements = document.select(".i-trabalhos-item")
        val trabalhos = ArrayList<EntregaTrabalhoDTO>()

        elements.forEach { e ->
            val dto = TrabalhoExtractor(e).getTrabalhoDTO()
            trabalhos.add(dto)
        }

        return trabalhos
    }
}