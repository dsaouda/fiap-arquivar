package com.github.dsaouda.fiap.hack.common

private var path = "";

fun basePath(base: String): String {
    return "${path}/${base}"
}

fun defaultPath(value: String) {
    path = value
}
