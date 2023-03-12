package com.koen.gosexam.core

import android.content.Context
import android.content.res.Resources
import androidx.core.content.ContextCompat
import com.koen.gosexam.R

class ColorResourceImpl(val context: Context) : ColorResource {
    override val gray100: Int
        get() = color(R.color.gray_100)

    fun color(color: Int) = ContextCompat.getColor(context, color)
}