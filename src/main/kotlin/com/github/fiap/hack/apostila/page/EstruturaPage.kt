package com.github.fiap.hack.apostila.page

import com.github.fiap.hack.common.JsoupFactory
import com.github.fiap.hack.common.url
import com.github.fiap.hack.apostila.parser.EstruturaParser
import org.jsoup.Connection

object EstruturaPage {

    fun request(cookie: String) = EstruturaParser(JsoupFactory.create(url("/programas/login/alunos_2004/apostilas_2007/_estrutura.asp"), cookie)
            .method(Connection.Method.POST)
            .header("X-Requested-With", "XMLHttpRequest")
            .header("X-Prototype-Version", "1.4.0")
            .execute()
            .parse()
            .select("#curso185_2016turma28SCJ_2016all .i-apostilas-item"), cookie)
}