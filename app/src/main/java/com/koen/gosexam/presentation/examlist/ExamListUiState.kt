package com.koen.gosexam.presentation.examlist

import com.koen.gosexam.extension.empty
import com.koen.gosexam.presentation.base.UiState
import com.koen.gosexam.presentation.models.ExamUi

data class ExamListUiState(
    val examList: List<ExamUi> = emptyList()
) : UiState {
}