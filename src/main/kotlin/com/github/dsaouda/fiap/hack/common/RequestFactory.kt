package com.github.dsaouda.fiap.hack.common

import org.jsoup.Connection

object RequestFactory {

    fun get(url: String, headers: Map<String, String>? = null) = create(url, Connection.Method.GET, null, headers)

    fun post(url: String, data: Map<String, String>, headers: Map<String, String>? = null) = create(url, Connection.Method.POST, data, headers)

    private fun create(url: String, method: Connection.Method, data: Map<String, String>?, headers: Map<String, String>?): Connection.Response {
        val conn = JsoupFactory.create(url)
                .method(method)

        data?.let {conn.data(data)}
        headers?.let {conn.headers(headers)}

        return conn.execute()
    }
}