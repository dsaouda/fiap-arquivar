package com.github.fiap.hack.common.factory

private var path = "";

fun basePath(base: String): String {
    return "${path}/${base}"
}

fun defaultPath(): String {
    return path
}

fun defaultPath(value: String) {
    path = path
}
