package com.koen.gosexam.presentation.models

data class ExamUi(
    val id: Int,
    val question: String,
    val answers: List<String>
)