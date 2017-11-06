package com.github.dsaouda.fiap.hack

import com.github.dsaouda.fiap.hack.common.defaultCookie
import com.github.dsaouda.fiap.hack.common.defaultPath
import org.apache.commons.cli.*


fun main(args: Array<String>) {
    val options = Options()
    options.addOption("c", true, "Cookie para ser usado para navegação")
    options.addOption("d", true, "Diretório para download dos arquivos")
    options.addOption("a", false, "Download apostila (use o comando -c e -d)")
    options.addOption("t", false, "Download trabalho (use o comando -c e -d)")

    val parser = DefaultParser()
    val cli = parser.parse(options, args)

    val path = cli.getOptionValue("d")
    if (path.isNullOrEmpty().not()) {
        defaultPath(path)
    }

    val cookie = cli.getOptionValue("c")
    if (cookie.isNullOrEmpty().not()) {
        defaultCookie(cookie)
    }

    when {
        cli.hasOption("t") -> com.github.dsaouda.fiap.hack.trabalho.cli()
        cli.hasOption("a") -> com.github.dsaouda.fiap.hack.apostila.cli()

        else -> {
            val formatter = HelpFormatter()
            formatter.printHelp("cli", options)
        }
    }
}