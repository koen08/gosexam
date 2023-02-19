package com.koen.gosexam.presentation.widget

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.koen.gosexam.R

fun changeBackground(check: Boolean, context: Context) : Drawable? {
    return if (check) {
        R.drawable.bg_solid_orange_100_4
    } else {
        R.drawable.bg_stroke_orange_100_4
    }.run {
        ContextCompat.getDrawable(context, this)
    }
}

fun changeTextColor(check: Boolean, context: Context) : Int {
    return if (check) {
        ContextCompat.getColor(context, R.color.primary_80)
    } else {
        ContextCompat.getColor(context, R.color.gray_100)
    }
}