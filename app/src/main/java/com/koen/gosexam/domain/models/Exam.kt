package com.koen.gosexam.domain.models

import com.koen.gosexam.core.DrawableResource
import com.koen.gosexam.core.StringResource
import com.koen.gosexam.presentation.models.ExamUi
import com.koen.gosexam.presentation.models.QuestionUi
import com.koen.gosexam.presentation.models.mapToPresentation

data class Exam(
    val id: Int,
    val question: String,
    val answers: List<String>
)

fun List<Exam>.mapToPresentation(stringResource: StringResource) = mapIndexed { index, exam ->
    QuestionUi(
        id = exam.id,
        question = exam.question,
        answers = exam.answers,
        numberQuestion = stringResource.getNumberQuestion((index + 1).toString())
    )
}

fun List<Exam>.mapToExamTestPresentation(
    stringResource: StringResource,
    drawableResource: DrawableResource
) = mapIndexedNotNull { index, exam ->
    return@mapIndexedNotNull if (exam.answers.isEmpty()) {
        null
    } else {
        ExamUi(
            id = exam.id,
            question = exam.question,
            answerTrue = exam.answers.first(),
            answers = exam.answers.mapIndexed { i, answer -> answer.mapToPresentation(drawableResource, i) }.shuffled(),
            positionQuestionTitle = stringResource.positionFromCommon((index + 1), this.size)
        )
    }

}