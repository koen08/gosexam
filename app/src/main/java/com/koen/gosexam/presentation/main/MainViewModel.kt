package com.koen.gosexam.presentation.main

import androidx.lifecycle.viewModelScope
import com.koen.gosexam.R
import com.koen.gosexam.domain.exam.GenerateExamUseCase
import com.koen.gosexam.domain.exam.GenerateRangeExamUseCase
import com.koen.gosexam.domain.exam.GetExamSizeUseCase
import com.koen.gosexam.domain.exam.GetExamUseCase
import com.koen.gosexam.extension.toIntOrZero
import com.koen.gosexam.presentation.base.BaseViewModel
import com.koen.gosexam.presentation.core.CurrentTab
import com.koen.gosexam.presentation.core.CurrentTab.Companion.isFirst
import com.koen.gosexam.presentation.core.Text
import com.koen.gosexam.presentation.dialog.info.InfoDialogBtnModel
import com.koen.gosexam.presentation.dialog.info.InfoDialogModel
import com.koen.gosexam.presentation.models.uiEvent.*
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
class MainViewModel @Inject constructor(
    private val getExamUseCase: GetExamUseCase,
    private val generateExamUseCase: GenerateExamUseCase,
    private val getExamSizeUseCase: GetExamSizeUseCase,
    private val generateRangeExamUseCase: GenerateRangeExamUseCase
) : BaseViewModel<MainUiState>() {

    companion object {
        private const val MIN_VALUE_SLIDER = 1
    }

    override val _uiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())
    override val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        initExam()
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

    fun generateExam(countQuestion: Int = uiState.value.currentText.toIntOrZero()) {
        val currentTab = uiState.value.currentTab
        if (currentTab.isFirst()) {
            viewModelScope.launch {
                val result = generateExamUseCase(countQuestion)
                sendEvent(OpenExamTest(result))
            }
        } else {
            viewModelScope.launch {
                val startRange = uiState.value.startRange
                val endRange = uiState.value.endRange
                val result = withContext(Dispatchers.IO) {
                    generateRangeExamUseCase(startRange, endRange)
                }
                sendEvent(OpenExamTest(result))
            }
        }
    }

    fun changeTabSelected(currentTab: CurrentTab) {
        _uiState.update { state ->
            state.copy(
                currentTab = currentTab
            )
        }
    }

    fun updateRange(
        startRange: Int = uiState.value.startRange,
        endRange: Int = uiState.value.startRange
    ) {
        _uiState.update { state ->
            val size = uiState.value.examSize
            val finishRange =
                if (endRange in 1..size) endRange else if (size > 0) size else 1049
            state.copy(
                startRange = if (startRange > 0) startRange else 1,
                endRange = finishRange
            )
        }
    }

    fun updateRangeAndSendEvent(
        startRange: Int = uiState.value.startRange,
        endRange: Int = uiState.value.endRange
    ) {
        var startRangeMutable = startRange
        var endRangeMutable = endRange
        if (startRange == uiState.value.startRange && endRange == uiState.value.endRange) {
            return
        }
        if (startRange > uiState.value.examSize) {
            startRangeMutable = uiState.value.examSize
        }
        if (endRange < startRangeMutable) {
            endRangeMutable = uiState.value.examSize
        }
        if (endRange > uiState.value.examSize) {
            endRangeMutable = uiState.value.examSize
        }
        if (startRangeMutable > 0 && endRangeMutable <= uiState.value.examSize) {
            sendEventShared(RangeSliderValues(startRangeMutable, endRangeMutable))
        }
    }

    fun sendEventRangeSlider(size : Int = uiState.value.examSize) {
        if (size > 1) {
            sendEventShared(RangeSliderInit(min = MIN_VALUE_SLIDER, max = size))
        }
    }

    private fun initExam() {
        viewModelScope.launch {
            sendEventShared(Loading)
            getExamUseCase().run {
                val size = withContext(Dispatchers.IO) {
                    getExamSizeUseCase()
                }
                _uiState.update { state ->
                    state.copy(
                        examSize = size
                    )
                }
                sendEventShared(DismissLoading)
                sendEventRangeSlider(size)
            }
        }
    }
}