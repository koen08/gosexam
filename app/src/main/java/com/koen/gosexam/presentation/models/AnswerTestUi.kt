package com.koen.gosexam.presentation.models

import android.os.Parcelable
import com.koen.gosexam.core.DrawableResource
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnswerTestUi(
    val text: String = "",
    val selected: Boolean = false,
    val isTrue: Boolean = false,
    val backgroundSelected: Int = 0
) : Parcelable

fun String.mapToPresentation(drawableResource: DrawableResource, index: Int) = AnswerTestUi(
    text = this,
    selected = false,
    isTrue = index == 0,
    backgroundSelected = drawableResource.getSelectedPrimary80ElseGray60(false)
)