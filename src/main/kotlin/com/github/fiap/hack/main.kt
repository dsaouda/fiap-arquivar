package com.github.fiap.hack

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
        com.github.fiap.hack.common.factory.defaultPath(path)
    }

    val cookie = cli.getOptionValue("c")
    if (cookie.isNullOrEmpty().not()) {
        com.github.fiap.hack.common.factory.defaultCookie(cookie)
    }

    when {
        cli.hasOption("t") -> com.github.fiap.hack.trabalho.cli()
        cli.hasOption("a") -> com.github.fiap.hack.apostila.cli(cookie)

        else -> {
            val formatter = HelpFormatter()
            formatter.printHelp("cli", options)
        }
    }
}