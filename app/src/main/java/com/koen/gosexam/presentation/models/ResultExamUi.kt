package com.koen.gosexam.presentation.models

import kotlinx.serialization.Serializable

@Serializable
data class ResultExamUi(
    val id: Int = 0,
    val question: String = "",
    val resultAnswerList: List<ResultAnswerUi> = emptyList()
)

@Serializable
data class ResultAnswerUi(
    val text: String = "",
    val selected: Boolean = false,
    val isTrue: Boolean = false,
    val textColor: Int = 0,
    val styleText: Int = 0
)