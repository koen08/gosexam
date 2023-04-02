package com.koen.gosexam.presentation.exam.result

import androidx.lifecycle.SavedStateHandle
import com.koen.gosexam.extension.getOrDefault
import com.koen.gosexam.presentation.base.BaseViewModel
import com.koen.gosexam.presentation.exam.result.ExamResultFragment.Companion.ARG_KEY_RESULT_UI
import com.koen.gosexam.presentation.models.ResultTestUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ExamResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ExamResultUiState>() {
    override val _uiState: MutableStateFlow<ExamResultUiState> = MutableStateFlow(ExamResultUiState(
        savedStateHandle.getOrDefault(
            ARG_KEY_RESULT_UI
        ) {
            ResultTestUi()
        }
    ))
    override val uiState: StateFlow<ExamResultUiState> = _uiState.asStateFlow()


}