package com.github.fiap.hack.common.factory

private var cookieValue = "";

fun cookie(): String {
    return cookieValue
}

fun cookie(value: String) {
    cookieValue = value
}
