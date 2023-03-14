package com.koen.gosexam.presentation.exam

import androidx.lifecycle.SavedStateHandle
import com.koen.gosexam.extension.getListOrEmpty
import com.koen.gosexam.extension.getOrDefault
import com.koen.gosexam.presentation.base.BaseViewModel
import com.koen.gosexam.presentation.exam.ExamFragment.Companion.KEY_ARG_EXAM_TEST_UI
import com.koen.gosexam.presentation.main.MainUiState
import com.koen.gosexam.presentation.models.ExamUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ExamUiState>() {
    override val _uiState: MutableStateFlow<ExamUiState> = MutableStateFlow(
        ExamUiState(
            savedStateHandle.getListOrEmpty(KEY_ARG_EXAM_TEST_UI)
        )
    )
    override val uiState: StateFlow<ExamUiState> = _uiState.asStateFlow()


    fun updateAnswerList() {

    }
}