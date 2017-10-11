package com.github.fiap.arquivar

import org.jsoup.Connection
import org.jsoup.Jsoup
import java.time.LocalDate
import java.time.format.DateTimeFormatter



fun main(args: Array<String>) {

    val text = "fDownload('7F5G999G8DCG7F5G41AG3C6G930G834G85EG38','3E8G44CG3FCG410G3FCG3C0G37');"
    val data = text.replace(Regex("fDownload|\\(|\\)|'|;"), "")
    println(data)


}