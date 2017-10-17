package com.github.fiap.hack.trabalho.dto

import com.github.fiap.hack.common.basePath
import java.io.File
import java.time.LocalDate

data class EntregaTrabalhoDTO(val codigoTrabalho: Int, val date: LocalDate, val status: String, val title: String, val subtitle: String, val nota: String) {

    fun dirname(): String {
        return basePath("${date} - ${title} (${subtitle})")
    }

    fun createDirectory() {
        val file = File(dirname())
        if (!file.exists()) {
            file.mkdirs()
        }
    }

    fun readmeContent() = """
           Title: ${title}
           Subtile: ${subtitle}
           Data de entrega: ${date}
           ${status}
           Nota: ${nota}
        """.trimIndent()

    fun writeContentReadme() = File("${dirname()}/README.md").writeText(readmeContent())

}