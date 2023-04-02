package com.koen.gosexam.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultTestUi(
    val countTrueAnswer: Int = 0,
    val commonAnswer: Int = 0,
    val textTitleResult : String = "",
    val textDescriptionResult: String = "",
    val examList: List<ResultExamUi> = emptyList(),
    val backgroundTitle: Int = 0
) : Parcelable