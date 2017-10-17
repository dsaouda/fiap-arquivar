package com.github.fiap.hack.common
import org.jsoup.Jsoup

object JsoupFactory {
    private const val REFERER = "https://www2.fiap.com.br/programas/login/alunos_2004/apostilas_2007/default.asp?titulo_secao=Apostilas"
    private const val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36"
    private const val HOST = "www2.fiap.com.br"
    private const val ORIGIN = "https://www2.fiap.com.br"
    const val BASE_URL = "https://www2.fiap.com.br"

    fun create(url: String, cookie: String) = Jsoup.connect(url)
            //.referrer(REFERER)
            .userAgent(USER_AGENT)
            .header("Host", HOST)
            .header("Origin", ORIGIN)
            .header("Cookie", cookie)

    fun create(url: String) = Jsoup.connect(url)
            //.referrer(REFERER)
            .userAgent(USER_AGENT)
            .ignoreContentType(true)
            .maxBodySize(0)
            .header("Host", HOST)
            .header("Origin", ORIGIN)
            .header("Cookie", defaultCookie())
}