package com.koen.gosexam.presentation.exam

import com.koen.gosexam.presentation.base.UiState
import com.koen.gosexam.presentation.models.ExamUi

data class ExamTestUiState(
    val examUiList: List<ExamUi>,
    val currentPosition: Int = 0,
    val btnText: String = "",
    val clickableAnswers: Boolean = true
) : UiState {
    val currentExam get() = examUiList.getOrNull(currentPosition)
}