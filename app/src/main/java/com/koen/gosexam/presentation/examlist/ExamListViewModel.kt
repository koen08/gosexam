package com.koen.gosexam.presentation.examlist

import com.koen.gosexam.presentation.base.BaseViewModel
import com.koen.gosexam.presentation.exam.ExamUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ExamListViewModel @Inject constructor(): BaseViewModel<ExamListUiState>() {
    override val _uiState: MutableStateFlow<ExamListUiState>
        get() = MutableStateFlow(ExamListUiState())
    override val uiState: StateFlow<ExamListUiState>
        get() = _uiState.asStateFlow()
}