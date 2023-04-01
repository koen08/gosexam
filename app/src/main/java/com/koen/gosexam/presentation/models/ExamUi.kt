package com.koen.gosexam.presentation.models

import android.os.Parcelable
import com.koen.gosexam.core.StringResource
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExamUi(
    val id: Int = 0,
    val question: String = "",
    val answerTrue: String = "",
    val answers: List<AnswerTestUi> = emptyList(),
    val positionQuestionTitle: String = ""
) : Parcelable


fun List<ExamUi>.mapToResult(stringResource: StringResource): ResultTestUi {
    val countTrueAnswer =
        this.filter { it.answers.find { element -> element.selected && element.isTrue } != null }.size
    return ResultTestUi(
        countTrueAnswer = countTrueAnswer,
        textTitleResult = stringResource.getTextTitleResult(countTrueAnswer, this.size),
        textDescriptionResult = stringResource.getResultCountAnswer(countTrueAnswer, this.size),
        commonAnswer = this.size,
        examList = this.map { it.mapToExamResultList() }
    )
}

fun ExamUi.mapToExamResultList(): ResultExamUi {
    return ResultExamUi(
        id = id,
        question = question,
        resultAnswerList = answers.map { it.mapToResultTestUi() }
    )
}

fun AnswerTestUi.mapToResultTestUi(): ResultAnswerUi {
    return ResultAnswerUi(
        text = text,
        selected = selected,
        isTrue = isTrue
    )
}