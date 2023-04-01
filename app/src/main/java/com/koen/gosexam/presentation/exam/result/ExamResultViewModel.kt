package com.koen.gosexam.presentation.exam.result

import com.koen.gosexam.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class ExamResultViewModel : BaseViewModel<ExamResultUiState>() {
    override val _uiState: MutableStateFlow<ExamResultUiState> = MutableStateFlow(ExamResultUiState())
    override val uiState: StateFlow<ExamResultUiState> = _uiState.asStateFlow()
}