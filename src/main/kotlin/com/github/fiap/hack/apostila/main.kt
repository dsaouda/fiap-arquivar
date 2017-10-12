package com.github.fiap.hack.apostila

import com.github.fiap.hack.apostila.page.EstruturaPage

private var cookie: String = ""

fun main(args: Array<String>) {
    cookie = args[0].trim()

    EstruturaPage.request(cookie).execute()
}