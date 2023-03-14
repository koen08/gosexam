package com.koen.gosexam.presentation.examlist

import com.koen.gosexam.presentation.base.UiState
import com.koen.gosexam.presentation.models.QuestionUi

data class ExamListUiState(
    val examList: List<QuestionUi> = emptyList()
) : UiState {
}