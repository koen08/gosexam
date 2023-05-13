package com.koen.gosexam.data.local.shared

interface SharedPrefString {
    fun save(obj: String)

    fun clear()

    fun get(): String
}