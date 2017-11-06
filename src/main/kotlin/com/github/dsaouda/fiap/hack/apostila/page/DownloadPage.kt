package com.github.dsaouda.fiap.hack.apostila.page

import com.github.dsaouda.fiap.hack.common.defaultCookie
import org.jsoup.Connection
import org.jsoup.Jsoup
import java.io.File
import java.time.LocalDate

class DownloadPage {
    fun download(diretorioDisciplina: File, paramDownlaod: String, arquivo: String, data: LocalDate) {

        when {
            paramDownlaod.indexOf("fDownloadArquivo") == 0 -> {
                println("download fDownloadArquivo ${arquivo} na data ${data}")
                val codigo = paramDownlaod.replace(Regex("fDownloadArquivo|\\(|\\)|'|;"), "")
                val file = File("${diretorioDisciplina.absolutePath}/${data} - ${arquivo}")
                if (!file.exists()) {
                    val conteudo = downloadArquivo(codigo)
                    file.writeBytes(conteudo)
                }

                return
            }

            paramDownlaod.indexOf("fDownload") == 0 -> {
                println("download fDownload ${arquivo} na data ${data}")

                val codigo = paramDownlaod.replace(Regex("fDownload|\\(|\\)|'|;"), "")
                val codigos = codigo.split(",")

                val file = File("${diretorioDisciplina.absolutePath}/${data} - ${arquivo}")
                if (!file.exists()) {
                    val conteudo = fDownload(codigos[0], codigos[1])
                    file.writeBytes(conteudo)
                }

                return
            }
        }
    }

    fun downloadArquivo(codigo: String): ByteArray {
        //<form name="frmDownload" id="frmDownload" method="post" action="login/alunos_2004/apostilas_2007/DownloadOutraExtensao.asp" target="iFrameAcao">
        //<input type="hidden" name="Apostila" id="Apostila" />
        //</form>

        val conn = Jsoup.connect("https://www2.fiap.com.br/programas/login/alunos_2004/apostilas_2007/DownloadOutraExtensao.asp")
                .method(Connection.Method.POST)
                .referrer("https://www2.fiap.com.br/programas/login/alunos_2004/apostilas_2007/default.asp?titulo_secao=Apostilas")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                .ignoreContentType(true)
                .maxBodySize(0)
                .header("Host", "www2.fiap.com.br")
                .header("Origin", "https://www2.fiap.com.br")
                .header("Cookie", defaultCookie())
                .header("Upgrade-Insecure-Requests", "1")
                .data("Apostila", codigo)

        return conn.execute().bodyAsBytes()
    }

    fun downloadLink(link: String): ByteArray {

        val conn = Jsoup.connect("https://www2.fiap.com.br/programas/${link}")
                .method(Connection.Method.GET)
                .referrer("https://www2.fiap.com.br/programas/login/alunos_2004/apostilas_2007/default.asp?titulo_secao=Apostilas")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                .ignoreContentType(true)
                .maxBodySize(0)
                .header("Host", "www2.fiap.com.br")
                .header("Origin", "https://www2.fiap.com.br")
                .header("Cookie", defaultCookie())
                .header("Upgrade-Insecure-Requests", "1");

        return conn.execute().bodyAsBytes()
    }

    fun fDownload(codigoA: String, codigoB: String): ByteArray {
        //<form action="./login/alunos_2004/apostilas_2007/download.asp" name="frm_download" id="frm_download" method="post">
        //<input type="hidden" name="a" id="a" value="">
        //<input type="hidden" name="c" id="c" value="">
        //</form>

        val conn = Jsoup.connect("https://www2.fiap.com.br/programas/login/alunos_2004/apostilas_2007/download.asp")
                .method(Connection.Method.POST)
                .referrer("https://www2.fiap.com.br/programas/login/alunos_2004/apostilas_2007/default.asp?titulo_secao=Apostilas")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36")
                .ignoreContentType(true)
                .maxBodySize(0)
                .header("Host", "www2.fiap.com.br")
                .header("Origin", "https://www2.fiap.com.br")
                .header("Cookie", defaultCookie())
                .header("Upgrade-Insecure-Requests", "1")
                .data("a", codigoA)
                .data("c", codigoB)

        return conn.execute().bodyAsBytes()
    }
}