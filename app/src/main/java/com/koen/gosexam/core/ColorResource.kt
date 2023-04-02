package com.koen.gosexam.core

interface ColorResource {
    val gray100: Int

    val greenSuccess: Int

    val error50: Int

    fun getSuccessOrFailOrDefault(isTrue: Boolean, isSelected: Boolean) : Int
}