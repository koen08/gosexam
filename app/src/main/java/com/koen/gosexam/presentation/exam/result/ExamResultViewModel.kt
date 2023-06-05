package com.koen.gosexam.presentation.exam.result

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.koen.gosexam.core.StringResource
import com.koen.gosexam.domain.exam.result.GetResultsExamByIdUseCase
import com.koen.gosexam.extension.getOrDefault
import com.koen.gosexam.presentation.base.BaseViewModel
import com.koen.gosexam.presentation.exam.result.ExamResultFragment.Companion.ARG_KEY_RESULT_UI
import com.koen.gosexam.presentation.models.ResultTestUi
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
class ExamResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getResultsExamByIdUseCase: GetResultsExamByIdUseCase,
    private val stringResource: StringResource
) : BaseViewModel<ExamResultUiState>() {
    override val _uiState = MutableStateFlow(ExamResultUiState())
    override val uiState: StateFlow<ExamResultUiState> = _uiState.asStateFlow()

    init {
        savedStateHandle.get<Long>(
            ARG_KEY_RESULT_UI
        )?.let { id ->
            viewModelScope.launch {
                withContext(Dispatchers.Default) { getResultsExamByIdUseCase(id) }.apply {
                    _uiState.update { state ->
                        state.copy(
                            examResultList = this@apply,
                            timerText = stringResource.getTimeTimeSpent(this.currentTimer)
                        )
                    }
                }
            }
        }
    }

}