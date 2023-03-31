package com.koen.gosexam.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExamUi(
    val id: Int = 0,
    val question: String = "",
    val answerTrue: String = "",
    val answers: List<AnswerTestUi> = emptyList(),
    val positionQuestionTitle: String = ""
) : Parcelable