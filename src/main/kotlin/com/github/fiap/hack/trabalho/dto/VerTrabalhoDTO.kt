package com.github.fiap.hack.trabalho.dto

import java.io.File
import java.nio.file.Paths

data class VerTrabalhoDTO(val linkArquivo: String) {

    fun getFilenameLink() = Paths.get(linkArquivo).fileName

    fun file(dirname: String) = File("${dirname}/${getFilenameLink()}")

    fun writeContent(dirname: String, content: ByteArray) {
        file(dirname).writeBytes(content)
    }

    fun writeContentIfFileNotExists(dirname: String, content: ByteArray): Boolean {
        if (file(dirname).exists().not()) {
            writeContent(dirname, content)
            return true
        }
        return false
    }
}