package com.github.fiap.hack.apostila.factory

fun url(uri: String): String {
    return "${JsoupFactory.BASE_URL}${uri}"
}