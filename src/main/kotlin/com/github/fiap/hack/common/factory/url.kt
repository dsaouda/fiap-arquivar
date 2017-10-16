package com.github.fiap.hack.common.factory

fun url(uri: String): String {
    return "${JsoupFactory.BASE_URL}${uri}"
}