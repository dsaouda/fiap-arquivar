package com.github.fiap.arquivar

import org.jsoup.Connection
import org.jsoup.Jsoup
import java.time.LocalDate
import java.time.format.DateTimeFormatter



fun main(args: Array<String>) {

    val text = "javascript:fPasta(this.getElementsByTagName('img')[0],this.getElementsByTagName('img')[1],'curso185_2016turma28SCJ_2016disciplina2522_1340_pasta3542all','','intCurso=185&intCursoAno=2016&strTurma=28SCJ&intTurmaAno=2016&intCodDisciplina=2522&intCodProfessor=1340&intCodPasta=3542&intAno=')"
    val regex = Regex("^.*?'','(.*?)'")
    val post1 = regex.find(text)?.groups?.get(1)?.value?.split("&")
    println(post1)


}