package com.koen.gosexam.presentation.exam

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.koen.gosexam.domain.exam.PrepareAnswerTestUseCase
import com.koen.gosexam.extension.getListOrEmpty
import com.koen.gosexam.presentation.base.BaseViewModel
import com.koen.gosexam.presentation.exam.ExamTestFragment.Companion.KEY_ARG_EXAM_TEST_UI
import com.koen.gosexam.presentation.models.AnswerTestUi
import com.koen.gosexam.presentation.models.ExamUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val prepareAnswerTestUseCase: PrepareAnswerTestUseCase
) : BaseViewModel<ExamTestUiState>() {
    override val _uiState: MutableStateFlow<ExamTestUiState> = MutableStateFlow(
        ExamTestUiState(
            savedStateHandle.getListOrEmpty(KEY_ARG_EXAM_TEST_UI)
        )
    )
    override val uiState: StateFlow<ExamTestUiState> = _uiState.asStateFlow()


    fun updateAnswerList(answerSelected: AnswerTestUi, examSelected: ExamUi) {
        viewModelScope.launch {
            val examNewList = prepareAnswerTestUseCase.invoke(
                answerSelected,
                examSelected,
                uiState.value.examUiList
            )
            updateExamUi(examNewList)
        }
    }

    private fun updateExamUi(examUiList: List<ExamUi>) {
        _uiState.update { state ->
            state.copy(
                examUiList = examUiList
            )
        }
    }
}