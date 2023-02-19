package com.koen.gosexam.presentation.exam

import com.koen.gosexam.presentation.base.BaseViewModel
import com.koen.gosexam.presentation.main.MainUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExamViewModel: BaseViewModel<ExamUiState>() {
    override val _uiState: MutableStateFlow<ExamUiState> = MutableStateFlow(ExamUiState())
    override val uiState: StateFlow<ExamUiState> = _uiState.asStateFlow()
}