package com.koen.gosexam.presentation.core

import android.content.Context

sealed class Text {
    abstract fun getText(context: Context) : String

    data class Resource(
        val resId: Int
    ) : Text() {
        override fun getText(context: Context): String = context.getString(resId)
    }

    data class Simple(
        val text: String
    ) : Text() {
        override fun getText(context: Context): String = text
    }
}