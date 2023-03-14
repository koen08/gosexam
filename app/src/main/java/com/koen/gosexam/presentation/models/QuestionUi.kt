package com.koen.gosexam.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuestionUi(
    val id: Int = 0,
    val question: String = "",
    val answers: List<String> = emptyList(),
    val numberQuestion: String = ""
) : Parcelable