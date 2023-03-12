package com.koen.gosexam.presentation.question.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.koen.gosexam.domain.question.PrepareFalseAnswerUseCase
import com.koen.gosexam.extension.getOrDefault
import com.koen.gosexam.presentation.base.BaseViewModel
import com.koen.gosexam.presentation.base.UiState
import com.koen.gosexam.presentation.models.ExamUi
import com.koen.gosexam.presentation.question.details.QuestionDetailsFragment.Companion.KEY_ARG_QUESTION_EXAM_UI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    val prepareFalseAnswerUseCase: PrepareFalseAnswerUseCase
) :
    BaseViewModel<QuestionDetailsUiState>() {
    override val _uiState: MutableStateFlow<QuestionDetailsUiState> =
        MutableStateFlow(QuestionDetailsUiState())
    override val uiState: StateFlow<QuestionDetailsUiState> = _uiState.asStateFlow()

    init {
        val examUi = savedStateHandle.getOrDefault(KEY_ARG_QUESTION_EXAM_UI) {
            ExamUi()
        }
        prepareQuestionDetails(examUi)
    }

    private fun prepareQuestionDetails(examUi: ExamUi) {
        prepareFalseAnswerUseCase(examUi).also { questionDetailsUi ->
            _uiState.update { state ->
                state.copy(
                    questionDetailsUi = questionDetailsUi
                )
            }
        }
    }
}