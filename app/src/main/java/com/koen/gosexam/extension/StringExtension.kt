package com.koen.gosexam.extension

val Any.empty get() = ""

fun String?.orEmpty() = this ?: ""