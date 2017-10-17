package com.github.fiap.hack.common

fun url(uri: String): String {
    return "${JsoupFactory.BASE_URL}${uri}"
}