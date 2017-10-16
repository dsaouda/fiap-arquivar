package com.github.fiap.hack.trabalho

import com.github.fiap.hack.common.factory.RequestFactory.get
import com.github.fiap.hack.common.factory.RequestFactory.post
import com.github.fiap.hack.common.factory.basePath
import com.github.fiap.hack.common.factory.url
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun extrairData(data: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yy")

    return LocalDate.parse(data, formatter)
}

fun cli() {
    val response = get(url("/programas/login/alunos_2004/entregaTrabalho/lista.asp?arquivado=1&titulo_secao=Entrega%20de%20Trabalhos"))

    response.parse().select(".i-trabalhos-item").forEach { e ->
        val codigoTrabalho = e.attr("href").replace(Regex("[^\\d]*"), "").trim()
        val date = extrairData(e.select(".i-trabalhos-date").text().trim())
        val status = e.select(".i-trabalhos-status").text().trim()
        val title = e.select(".i-trabalhos-title").text().replace(":", " -").replace("\"", "").trim()
        val subtitle = e.select(".i-trabalhos-subtitle").text().trim()
        val nota = e.select(".i-trabalhos-score").text().trim()

        val dirname = basePath("${date} - ${title} (${subtitle})");
        val file = File(dirname)
        if (!file.exists()) {
             file.mkdirs()
        }

        val readme = File("${dirname}/README.md")

        val content = """
           Title: ${title}
           Subtile: ${subtitle}
           Data de entrega: ${date}
           ${status}
           Nota: ${nota}
        """.trimIndent()
        readme.writeText(content)

        println(content)
        downloadTrabalho(codigoTrabalho, dirname)
        println("===============")

    }


}

fun downloadTrabalho(codigoTrabalho: String, dirname: String) {

    val response = post(url("/programas/login/alunos_2004/entregaTrabalho/verTrabalho.asp?titulo_secao=Entrega%20de%20Trabalhos"), mapOf(Pair("codigoTrabalho", codigoTrabalho)))

    val href = response.parse().select(".i-content-link").attr("href")

    val filename = Paths.get(href).fileName
    val gravar = File("${dirname}/${filename}")
    if (gravar.exists().not()) {
        println("Arquivo: ${filename}")

        val content = get(url(href))
        gravar.writeBytes(content.bodyAsBytes())
    }
}