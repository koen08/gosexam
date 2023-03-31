package com.koen.gosexam.core

interface DrawableResource {
    val bgSolidGray60Corners4: Int

    val bgSolidPrimary80Corners4: Int

    val bgSolidGreen: Int

    val bgSolidError50: Int

    fun getSelectedPrimary80ElseGray60(isSelected: Boolean): Int

    fun getSelectedIsTrueGreen50ElseGray60(isTrue: Boolean): Int

    fun getSelectedIsTrueGreen50ElseError50OrGray60(isTrue: Boolean, isSelected: Boolean): Int
}