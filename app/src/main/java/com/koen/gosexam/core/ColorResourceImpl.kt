package com.koen.gosexam.core

import android.content.Context
import android.content.res.Resources
import androidx.core.content.ContextCompat
import com.koen.gosexam.R

class ColorResourceImpl(val context: Context) : ColorResource {
    override val gray100: Int
        get() = color(R.color.gray_100)

    override val error50: Int
        get() = color(R.color.error_50)

    override val greenSuccess: Int
        get() = color(R.color.success_50)

    override fun getSuccessOrFailOrDefault(isTrue: Boolean, isSelected: Boolean): Int {
        return if (isTrue) {
            greenSuccess
        } else if (isSelected) {
            error50
        } else {
            gray100
        }
    }

    fun color(color: Int) = ContextCompat.getColor(context, color)
}