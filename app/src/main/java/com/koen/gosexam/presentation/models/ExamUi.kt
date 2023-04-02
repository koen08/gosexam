package com.koen.gosexam.presentation.models

import android.os.Parcelable
import com.koen.gosexam.core.ColorResource
import com.koen.gosexam.core.DrawableResource
import com.koen.gosexam.core.StringResource
import com.koen.gosexam.core.StyleResource
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExamUi(
    val id: Int = 0,
    val question: String = "",
    val answerTrue: String = "",
    val answers: List<AnswerTestUi> = emptyList(),
    val positionQuestionTitle: String = ""
) : Parcelable


fun List<ExamUi>.mapToResult(
    stringResource: StringResource,
    colorResource: ColorResource,
    styleResource: StyleResource,
    drawableResource: DrawableResource
): ResultTestUi {
    val countTrueAnswer =
        this.filter { it.answers.find { element -> element.selected && element.isTrue } != null }.size
    return ResultTestUi(
        countTrueAnswer = countTrueAnswer,
        textTitleResult = stringResource.getTextTitleResult(countTrueAnswer, this.size),
        textDescriptionResult = stringResource.getResultCountAnswer(countTrueAnswer, this.size),
        commonAnswer = this.size,
        examList = this.map { it.mapToExamResultList(colorResource, styleResource) },
        backgroundTitle = drawableResource.getSuccessOrFail(countTrueAnswer, this.size)
    )
}

fun ExamUi.mapToExamResultList(
    colorResource: ColorResource,
    styleResource: StyleResource,
): ResultExamUi {
    return ResultExamUi(
        id = id,
        question = question,
        resultAnswerList = answers.map { it.mapToResultTestUi(colorResource, styleResource) }
    )
}

fun AnswerTestUi.mapToResultTestUi(
    colorResource: ColorResource,
    styleResource: StyleResource,
): ResultAnswerUi {
    return ResultAnswerUi(
        text = text,
        selected = selected,
        isTrue = isTrue,
        textColor = colorResource.getSuccessOrFailOrDefault(isTrue, selected),
        styleText = styleResource.getDefaultBody
    )
}