package com.koen.gosexam.presentation.models

import android.os.Parcelable
import com.koen.gosexam.core.ColorResource
import com.koen.gosexam.core.DrawableResource
import com.koen.gosexam.core.StringResource
import com.koen.gosexam.core.StyleResource
import com.koen.gosexam.data.local.entity.ResultExamEntity
import kotlinx.parcelize.Parcelize

data class ResultTestUi(
    val countTrueAnswer: Int = 0,
    val isSuccess: Boolean = false,
    val commonAnswer: Int = 0,
    val textTitleResult: String = "",
    val textDescriptionResult: String = "",
    val examList: List<ResultExamUi> = emptyList(),
    val backgroundTitle: Int = 0,
    val isExam: Boolean = false,
    val currentTimer: Long = 0,
)

fun ResultTestUi.mapToEntity() = ResultExamEntity(
    countTrueAnswer = countTrueAnswer,
    isSuccess = isSuccess,
    commonAnswer = commonAnswer,
    examList = examList,
    currentTimer = currentTimer,
    isExam = isExam
)

fun ResultExamEntity.mapToUi(
    stringResource: StringResource,
    colorResource: ColorResource,
    styleResource: StyleResource,
    drawableResource: DrawableResource
): ResultTestUi {
    return ResultTestUi(
        countTrueAnswer = countTrueAnswer,
        isSuccess = isSuccess,
        textTitleResult = stringResource.getTextTitleResult(countTrueAnswer, commonAnswer),
        textDescriptionResult = stringResource.getResultCountAnswer(countTrueAnswer, commonAnswer),
        commonAnswer = commonAnswer,
        examList = examList,
        backgroundTitle = drawableResource.getSuccessOrFail(countTrueAnswer, commonAnswer),
        currentTimer = currentTimer,
        isExam = isExam
    )
}