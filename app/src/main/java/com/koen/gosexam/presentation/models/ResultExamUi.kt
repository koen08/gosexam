package com.koen.gosexam.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultExamUi(
    val id: Int = 0,
    val question: String = "",
    val resultAnswerList: List<ResultAnswerUi> = emptyList()
) : Parcelable

@Parcelize
data class ResultAnswerUi(
    val text: String = "",
    val selected: Boolean = false,
    val isTrue: Boolean = false
) : Parcelable