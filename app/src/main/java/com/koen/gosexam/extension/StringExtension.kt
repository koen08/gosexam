package com.koen.gosexam.extension

val Any.empty get() = ""

fun String?.orEmpty() = this ?: ""

fun String?.toIntOrZero() = this?.toInt() ?: 0