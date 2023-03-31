package com.koen.gosexam.presentation.exam

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.koen.gosexam.domain.exam.PrepareAnswerTestUseCase
import com.koen.gosexam.extension.getListOrEmpty
import com.koen.gosexam.presentation.base.BaseViewModel
import com.koen.gosexam.presentation.exam.ExamTestFragment.Companion.KEY_ARG_EXAM_TEST_UI
import com.koen.gosexam.presentation.models.AnswerTestUi
import com.koen.gosexam.presentation.models.ExamUi
import com.koen.gosexam.presentation.models.uiEvent.HideButton
import com.koen.gosexam.presentation.models.uiEvent.ShowButton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
            val examNewList = withContext(Dispatchers.IO) {
                prepareAnswerTestUseCase.invoke(
                    answerSelected,
                    examSelected,
                    uiState.value.examUiList
                )
            }
            updateExamUi(examNewList)
            showButton()
        }
    }

    fun updatePosition() {
        _uiState.update { state ->
            state.copy(
                currentPosition = state.currentPosition + 1
            )
        }
        hideButton()
    }

    private fun updateExamUi(examUiList: List<ExamUi>) {
        _uiState.update { state ->
            state.copy(
                examUiList = examUiList
            )
        }
    }

    private fun showButton() {
        sendEvent(ShowButton)
    }

    private fun hideButton() {
        sendEvent(HideButton)
    }
}