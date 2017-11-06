package com.github.dsaouda.fiap.hack.apostila

import com.github.dsaouda.fiap.hack.apostila.page.EstruturaPage

fun cli() = EstruturaPage.request().execute()
