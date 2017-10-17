package com.github.fiap.hack.trabalho.extrator

import com.github.fiap.hack.trabalho.dto.EntregaTrabalhoDTO
import org.jsoup.nodes.Element
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class TrabalhoExtractor(private val e: Element) {
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yy")

    fun getTrabalhoDTO(): EntregaTrabalhoDTO {
        val codigoTrabalho = e.attr("href").replace(Regex("[^\\d]*"), "").trim().toInt()
        val date = extrairData(e.select(".i-trabalhos-date").text().trim())
        val status = e.select(".i-trabalhos-status").text().trim()
        val title = e.select(".i-trabalhos-title").text().replace(":", " -").replace("\"", "").trim()
        val subtitle = e.select(".i-trabalhos-subtitle").text().trim()
        val nota = e.select(".i-trabalhos-score").text().trim()

        return EntregaTrabalhoDTO(codigoTrabalho, date, status, title, subtitle, nota)
    }

    private fun extrairData(data: String): LocalDate {
        return LocalDate.parse(data, formatter)
    }
}