package com.github.fiap.hack.trabalho

import com.github.fiap.hack.common.factory.RequestFactory.get
import com.github.fiap.hack.common.factory.RequestFactory.post
import com.github.fiap.hack.common.factory.cookie
import com.github.fiap.hack.common.factory.url
import java.io.File
import java.nio.file.Paths
import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun extrairData(data: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yy")

    return LocalDate.parse(data, formatter)
}

fun main(args: Array<String>) {


    cookie("nvgt46436=1505836522639_1_0|0_0|0; ASP.NET_SessionId=5520om1xmjircsvjfsrmt2vt; ASPSESSIONIDQWTQSRBC=DDAPNGEBFNNOIHJKFNJABEPI; jwt=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJybSI6IjMxMzY0Iiwibm9tZSI6IkRpZWdvIEhlbnJpcXVlIFNvdXNhIFNhb3VkYSIsImV4cCI6MTUwODE1NzQ2MiwiaXNzIjoiUG9ydGFsQWx1bm8iLCJhdWQiOiJodHRwczovL3d3dy5maWFwLmNvbS5ici8iLCJuYmYiOjE1MDgxNTM4NjJ9.bGpxv41etKyIcUNPugaKx64qp8QkRHB_ypcQjL08b_E; ultimoUsuarioFIAP=363G341G363G396G374G34; ASPSESSIONIDSWSQQSBC=BAINFOACMOPAFIFKNEPKKFPL; _ga=GA1.3.1419510416.1477061118; _gid=GA1.3.11419482.1508153834; _uetsid=_uetbbce5178; nvgc46436=0|0; nav46436=6a492b2745560796210e0c3be09|2_290; _bizo_bzid=20e43325-7200-4042-b8b1-0b402ca4912a; _bizo_cksm=40707143FFC0310F; _bizo_np_stats=155%3D793%2C; __utmt=1; __utma=40781493.1419510416.1477061118.1507571576.1508153889.70; __utmb=40781493.3.10.1508153889; __utmc=40781493; __utmz=40781493.1477061145.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none)")

    val response = get(url("/programas/login/alunos_2004/entregaTrabalho/lista.asp?arquivado=1&titulo_secao=Entrega%20de%20Trabalhos"))

    response.parse().select(".i-trabalhos-item").forEach { e ->
        val codigoTrabalho = e.attr("href").replace(Regex("[^\\d]*"), "").trim()
        val date = extrairData(e.select(".i-trabalhos-date").text().trim())
        val status = e.select(".i-trabalhos-status").text().trim()
        val title = e.select(".i-trabalhos-title").text().replace(":", " -").replace("\"", "").trim()
        val subtitle = e.select(".i-trabalhos-subtitle").text().trim()
        val nota = e.select(".i-trabalhos-score").text().trim()

        val file = File("data/trabalhos/${date} - ${title} (${subtitle})")
        if (!file.exists()) {
             file.mkdirs()
        }

        val readme = File("${file.absolutePath}/README.md")

        val content = """
           Title: ${title}
           Subtile: ${subtitle}
           Data de entrega: ${date}
           ${status}
           Nota: ${nota}
        """.trimIndent()
        readme.writeText(content)

        println(content)
        trabalho(codigoTrabalho, file)
        println("\n\n===============\n\n")

    }


}

fun trabalho(codigoTrabalho: String, file: File) {

    val response = post(url("/programas/login/alunos_2004/entregaTrabalho/verTrabalho.asp?titulo_secao=Entrega%20de%20Trabalhos"), mapOf(Pair("codigoTrabalho", codigoTrabalho)))

    val href = response.parse().select(".i-content-link").attr("href")

    val filename = Paths.get(href).fileName
    val gravar = File("${file.absolutePath}/${filename}")
    if (gravar.exists().not()) {
        println("Arquivo: ${filename}")

        val content = get(url(href))
        gravar.writeBytes(content.bodyAsBytes())
    }
}