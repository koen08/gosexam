package com.koen.gosexam.data.local.shared

interface StartFirstAppSharedPref {
    fun save(obj: Boolean)

    fun get(): Boolean
}