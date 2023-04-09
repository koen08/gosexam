package com.koen.gosexam.core

import android.content.Context
import androidx.core.content.ContextCompat
import com.koen.gosexam.R

class DrawableResourceImpl(val context: Context) : DrawableResource {
    override val bgSolidGray60Corners4: Int
        get() = R.drawable.bg_solid_gray_60_4
    override val bgSolidPrimary80Corners4: Int
        get() = R.drawable.bg_solid_orange_100_4
    override val bgSolidGreen: Int
        get() = R.drawable.bg_solid_green_50_4
    override val bgSolidError50: Int
        get() = R.drawable.bg_solid_error_50_4

    override fun getSelectedPrimary80ElseGray60(isSelected: Boolean): Int {
        return if (isSelected) {
            bgSolidPrimary80Corners4
        } else bgSolidGray60Corners4
    }

    override fun getSelectedIsTrueGreen50ElseGray60(isTrue: Boolean): Int {
        return if (isTrue) {
            bgSolidGreen
        } else {
            bgSolidGray60Corners4
        }
    }

    override fun getSelectedIsTrueGreen50ElseError50OrGray60(
        isTrue: Boolean,
        isSelected: Boolean
    ): Int {
        return if (isTrue) {
            bgSolidGreen
        } else if (isSelected) {
            bgSolidError50
        } else {
            bgSolidGray60Corners4
        }
    }

    override fun getSuccessOrFail(countAnswerTrue: Int, commonAnswerTrue: Int): Int {
        val result = countAnswerTrue.toFloat() / commonAnswerTrue.toFloat()
        return if (result >= 0.7) {
            bgSolidGreen
        } else {
            bgSolidError50
        }
    }
}