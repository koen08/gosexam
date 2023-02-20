package com.koen.gosexam.domain.models

import com.koen.gosexam.presentation.models.ExamUi

data class Exam(
    val id: Int,
    val question: String,
    val answers: List<String>
)

fun List<Exam>.mapToPresentation() = map {
    ExamUi(
        id = it.id,
        question = it.question,
        answers = it.answers
    )
}