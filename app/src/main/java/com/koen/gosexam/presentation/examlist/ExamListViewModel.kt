package com.koen.gosexam.presentation.examlist

import androidx.lifecycle.viewModelScope
import com.koen.gosexam.domain.exam.GetExamListUseCase
import com.koen.gosexam.presentation.base.BaseViewModel
import com.koen.gosexam.presentation.exam.ExamUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamListViewModel @Inject constructor(
    private val getExamListUseCase: GetExamListUseCase
): BaseViewModel<ExamListUiState>() {
    override val _uiState: MutableStateFlow<ExamListUiState> = MutableStateFlow(ExamListUiState())
    override val uiState: StateFlow<ExamListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val result = getExamListUseCase()
            _uiState.update { state ->
                state.copy(
                    examList = result
                )
            }
        }
    }
}