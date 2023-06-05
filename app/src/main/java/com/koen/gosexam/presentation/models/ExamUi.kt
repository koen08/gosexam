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
    drawableResource: DrawableResource,
    currentTimer: Long,
    isExam: Boolean
): ResultTestUi {
    val countTrueAnswer =
        this.filter { it.answers.find { element -> element.selected && element.isTrue } != null }.size
    return ResultTestUi(
        countTrueAnswer = countTrueAnswer,
        isSuccess = countTrueAnswer >= 0.7,
        textTitleResult = stringResource.getTextTitleResult(countTrueAnswer, this.size),
        textDescriptionResult = stringResource.getResultCountAnswer(countTrueAnswer, this.size),
        commonAnswer = this.size,
        examList = this.map { it.mapToExamResultList(colorResource, styleResource) },
        backgroundTitle = drawableResource.getSuccessOrFail(countTrueAnswer, this.size),
        currentTimer = currentTimer,
        isExam = isExam
    )
}

fun ExamUi.mapToExamResultList(
    colorResource: ColorResource,
    styleResource: StyleResource,
): ResultExamUi {
    return ResultExamUi(
        id = id,
        question = question,
        resultAnswerList = answers.mapIndexed { index, item ->
            item.mapToResultTestUi(
                colorResource,
                styleResource,
                index + 1
            )
        }
    )
}

fun AnswerTestUi.mapToResultTestUi(
    colorResource: ColorResource,
    styleResource: StyleResource,
    index: Int
): ResultAnswerUi {
    return ResultAnswerUi(
        text = "$index. $text",
        selected = selected,
        isTrue = isTrue,
        textColor = colorResource.getSuccessOrFailOrDefault(isTrue, selected),
        styleText = styleResource.getDefaultBody
    )
}