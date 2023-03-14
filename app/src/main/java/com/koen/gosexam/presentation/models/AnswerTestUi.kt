package com.koen.gosexam.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AnswerTestUi(
    val text : String = "",
    val selected: Boolean = false
) : Parcelable

fun String.mapToPresentation() = AnswerTestUi(
    text = this,
    selected = false
)