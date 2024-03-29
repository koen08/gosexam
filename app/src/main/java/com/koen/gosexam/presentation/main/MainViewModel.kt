package com.koen.gosexam.presentation.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.koen.gosexam.R
import com.koen.gosexam.core.StringResource
import com.koen.gosexam.domain.exam.GenerateExamUseCase
import com.koen.gosexam.domain.exam.GenerateRangeExamUseCase
import com.koen.gosexam.domain.exam.GetExamSizeUseCase
import com.koen.gosexam.domain.exam.GetExamUseCase
import com.koen.gosexam.domain.exam.GetSizeExamFlowUseCase
import com.koen.gosexam.domain.start.GetStartFirstAppUseCase
import com.koen.gosexam.domain.start.SaveStartFirstAppUseCase
import com.koen.gosexam.extension.isValid
import com.koen.gosexam.extension.toIntOrZero
import com.koen.gosexam.presentation.base.BaseViewModel
import com.koen.gosexam.presentation.core.CurrentTab
import com.koen.gosexam.presentation.core.CurrentTab.Companion.isFirst
import com.koen.gosexam.presentation.core.Text
import com.koen.gosexam.presentation.dialog.info.InfoDialogBtnModel
import com.koen.gosexam.presentation.dialog.info.InfoDialogModel
import com.koen.gosexam.presentation.models.SettingsExam
import com.koen.gosexam.presentation.models.uiEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Random
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getExamUseCase: GetExamUseCase,
    private val stringResource: StringResource,
    private val getSizeExamFlow: GetSizeExamFlowUseCase,
    private val getStartFirstAppUseCase: GetStartFirstAppUseCase,
) : BaseViewModel<MainUiState>() {

    companion object {
        private const val MIN_VALUE_SLIDER = 1
        private const val MAX_VALUE_EXAM_MODE = "60"
    }

    override val _uiState = MutableStateFlow(MainUiState())
    override val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    var textStartRange = ""
    var testEndRange = ""

    init {
        initExam()
        collectExamFlowUseCase()
    }

    fun updateStartTextRange(text: String) {
        textStartRange = text
    }

    fun updateEndTextRange(text: String) {
        testEndRange = text
    }

    fun changeTypeExam(typeExam: TypeExam) {
        _uiState.update { state ->
            state.copy(
                typeExam = typeExam
            )
        }
    }

    fun changeButtonHelpersActive(buttonHelpers: ButtonHelpers) {
        val textBtnHelper =
            if (buttonHelpers == ButtonHelpers.MAX) uiState.value.examSize.toString() else buttonHelpers.value
        _uiState.update { state ->
            val lastButtonHelper = state.buttonHelpers == buttonHelpers
            state.copy(
                buttonHelpers = if (lastButtonHelper) ButtonHelpers.NO_ACTIVE else buttonHelpers,
                currentText = if (lastButtonHelper) ButtonHelpers.NO_ACTIVE.value else textBtnHelper
            )
        }
    }

    fun changeButtonHelpersChangeText(buttonHelpers: ButtonHelpers, currentText: String) {
        if (buttonHelpers != ButtonHelpers.NO_ACTIVE) {
            if (buttonHelpers == ButtonHelpers.MAX) uiState.value.examSize.toString() else buttonHelpers.value
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

    fun generateExam() {
        val currentTab = uiState.value.currentTab
        if (currentTab.isFirst() && uiState.value.currentText.length >= 4) {
            try {
                if (uiState.value.currentText.toIntOrZero() > uiState.value.examSize) {
                    sendEventShared(ErrorTextInput(stringResource.errorLarge))
                    return
                }
            } catch (e: java.lang.NumberFormatException) {
                sendEventShared(ErrorTextInput(stringResource.errorLarge))
                return
            }
        }
        val countQuestion = uiState.value.currentText.toIntOrZero()
        if (currentTab.isFirst() && countQuestion == 0) {
            sendEventShared(ErrorTextInput(stringResource.errorEmptyTextInput))
            return
        }
        if (currentTab.isFirst()) {
            SettingsExam.RandomTest(
                countQuestion = countQuestion,
                isExamMode = uiState.value.examMode == MainUiState.ExamMode.EXAM
            )
        } else {
            val startRange = uiState.value.startRange
            val endRange = uiState.value.endRange
            SettingsExam.RangeTest(
                startRange = startRange,
                endRange = endRange,
                isRandomPosition = uiState.value.isRandomRange
            )
        }.let {
            sendEvent(OpenExamTest(it))
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
                if (endRange in 1..size) endRange else if (size > 0) size else uiState.value.examSize
            state.copy(
                startRange = if (startRange > 0) startRange else 1,
                endRange = finishRange
            )
        }
    }

    fun updateRangeAndSendEvent(
        startRange: String = uiState.value.startRange.toString(),
        endRange: String = uiState.value.endRange.toString()
    ) {
        if (!startRange.isValid() || !endRange.isValid()) {
            return
        }
        val startRangeInt = startRange.toInt()
        val endRangeInt = endRange.toInt()
        var startRangeMutable = startRangeInt
        var endRangeMutable = endRangeInt
        if (startRangeInt == uiState.value.startRange && endRangeInt == uiState.value.endRange) {
            return
        }
        if (startRangeInt > uiState.value.examSize) {
            startRangeMutable = uiState.value.examSize
        }
        if (endRangeInt < startRangeMutable) {
            endRangeMutable = endRangeInt
        }
        if (endRangeInt > uiState.value.examSize) {
            endRangeMutable = uiState.value.examSize
        }
        if (endRangeInt <= 0) {
            endRangeMutable = startRangeMutable + 1
        }
        if (startRangeMutable > 0 && endRangeMutable <= uiState.value.examSize) {
            sendEventShared(RangeSliderValues(startRangeMutable, endRangeMutable))
        }
    }

    fun sendEventRangeSlider(size: Int = uiState.value.examSize) {
        if (size > 1) {
            sendEventShared(RangeSliderInit(min = MIN_VALUE_SLIDER, max = size))
        }
    }

    fun checkIsFirstApp() {
        viewModelScope.launch {
            val isFirstStartApp = getStartFirstAppUseCase()
            if (isFirstStartApp) {
                sendEventShared(OpenSelectionFaculty)
            }
        }
    }

    fun initExam() {
        viewModelScope.launch {
            sendEventShared(Loading)
            getExamUseCase().run {
                sendEventShared(DismissLoading)
            }
            _uiState.update { state ->
                state.copy(
                    btnExamText = stringResource.getBtnExamTest(true)
                )
            }
        }
    }

    fun showAdsOrGenerateTest() {
        val random = Random()
        val nextInt = random.nextInt(100)
        if (nextInt < 25) {
            sendEventShared(ShowAds)
        } else {
            generateExam()
        }
    }

    fun sendShowAds() {
        viewModelScope.launch {
            delay(2000)
            sendEventShared(ShowAds)
        }
    }

    fun updateRandomRange(isCheck: Boolean) {
        _uiState.update { state ->
            state.copy(
                isRandomRange = isCheck
            )
        }
    }

    fun updateModeExam() {
        _uiState.update { state ->
            val examMode = if (state.examMode == MainUiState.ExamMode.WORKOUT)
                MainUiState.ExamMode.EXAM
            else MainUiState.ExamMode.WORKOUT
            val visibleBtnMini = examMode == MainUiState.ExamMode.WORKOUT
            val currentText = if (examMode == MainUiState.ExamMode.WORKOUT) "" else MAX_VALUE_EXAM_MODE
            sendEventShared(StartAnim(visibleBtnMini))
            state.copy(
                examMode = examMode,
                visibleBtnMini = visibleBtnMini,
                currentText = currentText,
                btnExamText = stringResource.getBtnExamTest(examMode == MainUiState.ExamMode.WORKOUT)
            )
        }
    }

    private fun collectExamFlowUseCase() {
        viewModelScope.launch {
            getSizeExamFlow.invoke().collect { size ->
                _uiState.update { state ->
                    state.copy(
                        examSize = size,
                        textInfoTest = stringResource.getTextInfoTest(size.toString())
                    )
                }
                sendEventRangeSlider(size)
            }
        }
    }
}