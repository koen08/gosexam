package com.koen.gosexam.presentation.main

import androidx.lifecycle.viewModelScope
import com.koen.gosexam.R
import com.koen.gosexam.domain.exam.GetExamUseCase
import com.koen.gosexam.presentation.base.BaseViewModel
import com.koen.gosexam.presentation.core.Text
import com.koen.gosexam.presentation.dialog.info.InfoDialogBtnModel
import com.koen.gosexam.presentation.dialog.info.InfoDialogModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getExamUseCase: GetExamUseCase
) : BaseViewModel<MainUiState>() {
    override val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    override val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            getExamUseCase()
        }
    }

    fun changeTypeExam(typeExam: TypeExam) {
        _uiState.update { state ->
            state.copy(
                typeExam = typeExam
            )
        }
    }

    fun changeButtonHelpersActive(buttonHelpers: ButtonHelpers) {
        _uiState.update { state ->
            val lastButtonHelper = state.buttonHelpers == buttonHelpers
            state.copy(
                buttonHelpers = if (lastButtonHelper) ButtonHelpers.NO_ACTIVE else buttonHelpers,
                currentText = if (lastButtonHelper) ButtonHelpers.NO_ACTIVE.value else buttonHelpers.value
            )
        }
    }

    fun changeButtonHelpersChangeText(buttonHelpers: ButtonHelpers, currentText: String) {
        if (buttonHelpers != ButtonHelpers.NO_ACTIVE) {
            buttonHelpers.value
        } else {
            currentText
        }.also { newText ->
            _uiState.update { state ->
                state.copy(
                    buttonHelpers = buttonHelpers,
                    currentText = newText
                )
            }
        }
    }

    fun onClickInfoTypeExam() {
        val infoDialog = InfoDialogModel(
            title = Text.Resource(R.string.main_infoDialogTypeExamTitle),
            text = Text.Resource(R.string.main_infoDialogTypeExamContent),
            infoDialogBtnModel = InfoDialogBtnModel(textBtn = Text.Resource(R.string.infoDialog_close))
        )
        viewModelScope.launch {
            _infoDialogSharedFlow.emit(
                infoDialog
            )
        }
    }
}