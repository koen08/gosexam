package com.koen.gosexam.core

interface DrawableResource {
    val bgSolidGray60Corners4: Int

    val bgSolidPrimary80Corners4: Int

    fun getSelectedPrimary80ElseGray60(isSelected : Boolean) : Int
}