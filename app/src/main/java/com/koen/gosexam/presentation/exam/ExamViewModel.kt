package com.koen.gosexam.presentation.exam

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.koen.gosexam.core.StringResource
import com.koen.gosexam.domain.exam.GenerateExamUseCase
import com.koen.gosexam.domain.exam.GenerateRangeExamUseCase
import com.koen.gosexam.domain.exam.PrepareAnswerTestExamModeUseCase
import com.koen.gosexam.domain.exam.PrepareAnswerTestUseCase
import com.koen.gosexam.domain.exam.PrepareResultTestUseCase
import com.koen.gosexam.extension.getListOrEmpty
import com.koen.gosexam.presentation.base.BaseViewModel
import com.koen.gosexam.presentation.exam.ExamTestFragment.Companion.KEY_ARG_EXAM_TEST_UI
import com.koen.gosexam.presentation.exam.ExamTestUiState.ExamMode.Companion.isExam
import com.koen.gosexam.presentation.models.AnswerTestUi
import com.koen.gosexam.presentation.models.ExamUi
import com.koen.gosexam.presentation.models.SettingsExam
import com.koen.gosexam.presentation.models.uiEvent.HideButton
import com.koen.gosexam.presentation.models.uiEvent.OpenResultTest
import com.koen.gosexam.presentation.models.uiEvent.ShowAds
import com.koen.gosexam.presentation.models.uiEvent.ShowButton
import com.koen.gosexam.presentation.models.uiEvent.TimerTicket
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ExamViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val prepareAnswerTestUseCase: PrepareAnswerTestUseCase,
    private val prepareAnswerTestExamModeUseCase: PrepareAnswerTestExamModeUseCase,
    private val prepareResultTestUseCase: PrepareResultTestUseCase,
    private val stringResource: StringResource,
    private val generateExamUseCase: GenerateExamUseCase,
    private val generateRangeExamUseCase: GenerateRangeExamUseCase,
) : BaseViewModel<ExamTestUiState>() {
    override val _uiState: MutableStateFlow<ExamTestUiState> = MutableStateFlow(
        ExamTestUiState(
            settingsExam = savedStateHandle.get<SettingsExam>(KEY_ARG_EXAM_TEST_UI)
                ?: SettingsExam.RandomTest(
                    countQuestion = 1,
                    isExamMode = false
                )
        )
    )
    override val uiState: StateFlow<ExamTestUiState> = _uiState.asStateFlow()

    private var currentLastTime = 0L

    private val timer = object : CountDownTimer(3600000, 1_000) {
        override fun onTick(millisUntilFinished: Long) {
            currentLastTime = millisUntilFinished
            val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
            val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(minutes)
            val result = String.format("%d:%02d", minutes, seconds)
            sendEvent(TimerTicket(
                stringResource.getLastTime(result)
            ))
        }

        override fun onFinish() {
            sendEvent(ShowAds)
        }
    }

    init {
        launchExam()
        updateExamMode()
    }

    override fun onCleared() {
        timer.cancel()
        super.onCleared()
    }

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
            val examUiListWithExamMode = if (uiState.value.examMode.isExam()) {
                withContext(Dispatchers.IO) {
                    prepareAnswerTestExamModeUseCase.invoke(
                        answerSelected,
                        examSelected,
                        uiState.value.examUiList
                    )
                }
            } else emptyList()
            val position = uiState.value.currentPosition
            val btnText = stringResource.getTextBtnCompleteOrNext(
                complete = position + 1 >= examNewList.size
            )
            updateExamUi(examNewList)
            updateExamUiExamMode(examUiListWithExamMode)
            updateBtnText(btnText)
            updateClickableAnswers(false)
            showButton()
        }
    }

    fun updatePosition() {
        val uiState = uiState.value
        if (uiState.currentPosition + 1 >= uiState.examUiList.size) {
            timer.cancel()
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

    fun sendShowAds() {
        viewModelScope.launch {
            if (uiState.value.isLoadSuccessAds == ExamTestUiState.LoadingStateAds.NONE) {
                delay(2000)
                sendEvent(ShowAds)
            } else if (uiState.value.isLoadSuccessAds == ExamTestUiState.LoadingStateAds.FAILED) {
                prepareResult()
            }
        }
    }

    fun prepareResult(examUiList: List<ExamUi> = uiState.value.examUiList) {
        viewModelScope.launch {
            val resultList = withContext(Dispatchers.IO) {
                prepareResultTestUseCase(examUiList, currentLastTime, uiState.value.examMode.isExam())
            }
            sendEvent(OpenResultTest(resultList))
            timer.cancel()
        }
    }

    fun updateSuccessAds(stateLoading: ExamTestUiState.LoadingStateAds) {
        _uiState.update { state ->
            state.copy(
                isLoadSuccessAds = stateLoading
            )
        }
    }

    private fun launchExam() {
        viewModelScope.launch {
            val examSettings = uiState.value.settingsExam
            if (examSettings is SettingsExam.RandomTest) {
                withContext(Dispatchers.IO) { generateExamUseCase(countQuestion = examSettings.countQuestion) }.also {
                    updateExamUi(
                        examUiList = it
                    )
                    if (examSettings.isExamMode) {
                        timer.start()
                        updateExamUiExamMode(examUiList = it)
                    }
                }
            } else if (examSettings is SettingsExam.RangeTest) {
                withContext(Dispatchers.IO) {
                    generateRangeExamUseCase(
                        minIndex = examSettings.startRange,
                        maxIndex = examSettings.endRange,
                        isRandom = examSettings.isRandomPosition
                    )
                }.also {
                    updateExamUi(
                        examUiList = it
                    )
                }
            }
        }
    }

    private fun updateExamUiExamMode(examUiList: List<ExamUi>) {
        _uiState.update { state ->
            state.copy(
                examUiListWithExamMode = examUiList
            )
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

    private fun updateExamMode() {
        val settings = uiState.value.settingsExam
        if (settings is SettingsExam.RandomTest) {
            _uiState.update { state ->
                state.copy(
                    examMode = ExamTestUiState.ExamMode.getExamMode(isExam = settings.isExamMode)
                )
            }
        }
    }
}