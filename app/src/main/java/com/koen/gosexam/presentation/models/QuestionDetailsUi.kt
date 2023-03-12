package com.koen.gosexam.presentation.models

data class QuestionDetailsUi(
    val id : Int = 0,
    val question: String = "",
    val answers: List<String> = emptyList(),
    val titleNumber: String = "",
    val answerFalseStyle : Int = 0,
    val answerFalseColor : Int = 0,
    val answerFalseText: List<String> = emptyList()
)