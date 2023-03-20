package com.koen.gosexam.core

import android.content.Context
import androidx.core.content.ContextCompat
import com.koen.gosexam.R

class DrawableResourceImpl(val context: Context) : DrawableResource {
    override val bgSolidGray60Corners4: Int
        get() = R.drawable.bg_solid_gray_60_4
    override val bgSolidPrimary80Corners4: Int
        get() =  R.drawable.bg_solid_orange_100_4

    override fun getSelectedPrimary80ElseGray60(isSelected: Boolean): Int {
        return if (isSelected) {
            bgSolidPrimary80Corners4
        } else bgSolidGray60Corners4
    }
}