package com.github.dsaouda.fiap.hack.common

private var cookieValue = "";

fun defaultCookie(): String {
    return cookieValue
}

fun defaultCookie(value: String) {
    cookieValue = value
}
