package com.koen.gosexam.presentation.exam

import com.koen.gosexam.presentation.base.UiState
import com.koen.gosexam.presentation.models.ExamUi

data class ExamUiState(
    val examUiList: List<ExamUi>
) : UiState