package com.koen.gosexam.domain.models

import com.koen.gosexam.core.StringResource
import com.koen.gosexam.presentation.models.ExamUi

data class Exam(
    val id: Int,
    val question: String,
    val answers: List<String>
)

fun List<Exam>.mapToPresentation(stringResource: StringResource) = map {
    ExamUi(
        id = it.id,
        question = it.question,
        answers = it.answers,
        numberQuestion = stringResource.getNumberQuestion(it.id.toString())
    )
}