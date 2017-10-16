package com.github.fiap.hack.apostila

import com.github.fiap.hack.apostila.page.EstruturaPage

fun cli(cookie: String) = EstruturaPage.request(cookie).execute()
