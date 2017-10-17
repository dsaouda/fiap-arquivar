package com.github.fiap.hack.apostila.page

import com.github.fiap.hack.common.JsoupFactory
import com.github.fiap.hack.common.url
import org.jsoup.Connection
import org.jsoup.nodes.Document

class ArquivoPastaPage {

    fun request(data: List<String>?, cookie: String): Document {
        val conn = JsoupFactory.create(url("/programas/login/alunos_2004/apostilas_2007/_arquivosPasta.asp"), cookie)
                .method(Connection.Method.POST)
                .header("X-Requested-With", "XMLHttpRequest")
                .header("X-Prototype-Version", "1.4.0")

        data?.forEach { d ->
            val valor = d.split("=")
            conn.data(valor[0], valor[1])
        }

        return conn.execute().parse()
    }

}