package com.github.dsaouda.fiap.hack.apostila.page

import com.github.dsaouda.fiap.hack.common.JsoupFactory
import com.github.dsaouda.fiap.hack.common.url
import com.github.dsaouda.fiap.hack.apostila.parser.EstruturaParser
import org.jsoup.Connection

object EstruturaPage {

    fun request() = EstruturaParser(JsoupFactory.create(url("/programas/login/alunos_2004/apostilas_2007/_estrutura.asp"))
            .method(Connection.Method.POST)
            .header("X-Requested-With", "XMLHttpRequest")
            .header("X-Prototype-Version", "1.4.0")
            .execute()
            .parse()
            .select("#curso185_2016turma28SCJ_2016all .i-apostilas-item"))
}