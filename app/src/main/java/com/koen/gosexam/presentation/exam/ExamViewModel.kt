package com.koen.gosexam.presentation.exam

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.koen.gosexam.core.StringResource
import com.koen.gosexam.domain.exam.PrepareAnswerTestUseCase
import com.koen.gosexam.domain.exam.PrepareResultTestUseCase
import com.koen.gosexam.extension.getListOrEmpty
import com.koen.gosexam.presentation.base.BaseViewModel
import com.koen.gosexam.presentation.exam.ExamTestFragment.Companion.KEY_ARG_EXAM_TEST_UI
import com.koen.gosexam.presentation.models.AnswerTestUi
import com.koen.gosexam.presentation.models.ExamUi
import com.koen.gosexam.presentation.models.uiEvent.HideButton
import com.koen.gosexam.presentation.models.uiEvent.OpenResultTest
import com.koen.gosexam.presentation.models.uiEvent.ShowAds
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
    private val prepareAnswerTestUseCase: PrepareAnswerTestUseCase,
    private val prepareResultTestUseCase: PrepareResultTestUseCase,
    private val stringResource: StringResource
) : BaseViewModel<ExamTestUiState>() {
    override val _uiState: MutableStateFlow<ExamTestUiState> = MutableStateFlow(
        ExamTestUiState(
            savedStateHandle.getListOrEmpty(KEY_ARG_EXAM_TEST_UI)
        )
    )
    override val uiState: StateFlow<ExamTestUiState> = _uiState.asStateFlow()

    fun updateAnswerList(answerSelected: AnswerTestUi, examSelected: ExamUi) {
        if (!uiState.value.clickableAnswers) {
            return
        }
        viewModelScope.launch {
            val examNewList = withContext(Dispatchers.IO) {
                prepareAnswerTestUseCase.invoke(
                    answerSelected,
                    examSelected,
                    uiState.value.examUiList
                )
            }
            val position = uiState.value.currentPosition
            val btnText = stringResource.getTextBtnCompleteOrNext(
                complete = position + 1 >= examNewList.size
            )
            updateExamUi(examNewList)
            updateBtnText(btnText)
            updateClickableAnswers(false)
            showButton()
        }
    }

    fun updatePosition() {
        val uiState = uiState.value
        if (uiState.currentPosition + 1 >= uiState.examUiList.size) {
            sendEvent(ShowAds)
        } else {
            _uiState.update { state ->
                state.copy(
                    currentPosition = state.currentPosition + 1
                )
            }
            updateClickableAnswers(true)
            hideButton()
        }
    }

    fun prepareResult(examUiList: List<ExamUi> = uiState.value.examUiList) {
        viewModelScope.launch {
            val resultList = withContext(Dispatchers.IO) {
                prepareResultTestUseCase(examUiList)
            }
            sendEvent(OpenResultTest(resultList))
        }
    }

    private fun updateExamUi(examUiList: List<ExamUi>) {
        _uiState.update { state ->
            state.copy(
                examUiList = examUiList
            )
        }
    }

    private fun updateBtnText(btnText: String) {
        _uiState.update { state ->
            state.copy(
                btnText = btnText
            )
        }
    }

    private fun updateClickableAnswers(clickable: Boolean) {
        _uiState.update { state ->
            state.copy(
                clickableAnswers = clickable
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