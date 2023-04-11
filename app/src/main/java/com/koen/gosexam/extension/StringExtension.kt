package com.koen.gosexam.extension

val Any.empty get() = ""

fun String?.orEmpty() = this ?: ""

fun String.isValid() = this.isNotEmpty() && this.isNotBlank()

fun String?.toIntOrZero() : Int {
    if (this == null) {
        return 0
    }
    return if (!this.isValid()) {
        0
    } else this.toInt()
}

fun String?.toLongOrZero() : Long {
    if (this == null) {
        return 0
    }
    return if (!this.isValid()) {
        0
    } else this.toLong()
}