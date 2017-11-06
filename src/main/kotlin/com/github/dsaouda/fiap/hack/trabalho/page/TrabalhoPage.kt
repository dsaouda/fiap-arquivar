package com.github.dsaouda.fiap.hack.trabalho.page

import com.github.dsaouda.fiap.hack.common.RequestFactory.get
import com.github.dsaouda.fiap.hack.common.RequestFactory.post
import com.github.dsaouda.fiap.hack.common.url
import com.github.dsaouda.fiap.hack.trabalho.parser.EntregaTrabalhoParser
import com.github.dsaouda.fiap.hack.trabalho.parser.VerTrabalhoParser

object TrabalhoPage {

    val URL_LISTA_TRABALHOS = url("/programas/login/alunos_2004/entregaTrabalho/lista.asp?arquivado=1&titulo_secao=Entrega%20de%20Trabalhos")
    val URL_TRABALHO = url("/programas/login/alunos_2004/entregaTrabalho/verTrabalho.asp?titulo_secao=Entrega%20de%20Trabalhos")

    fun lista() = EntregaTrabalhoParser(get(URL_LISTA_TRABALHOS))

    fun verTrabalho(codigoTrabalho: Int) = VerTrabalhoParser(post(URL_TRABALHO, mapOf(Pair("codigoTrabalho", codigoTrabalho.toString()))))

    fun download(link: String) = get(url(link)).bodyAsBytes()
}