package com.koen.gosexam.presentation.exam

import com.koen.gosexam.presentation.base.UiState
import com.koen.gosexam.presentation.models.ExamUi
import com.koen.gosexam.presentation.models.SettingsExam

data class ExamTestUiState(
    val examUiList: List<ExamUi> = emptyList(),
    val settingsExam: SettingsExam,
    val currentPosition: Int = 0,
    val btnText: String = "",
    val clickableAnswers: Boolean = true,
    val isLoadSuccessAds: LoadingStateAds = LoadingStateAds.NONE,
) : UiState {
    val currentExam get() = examUiList.getOrNull(currentPosition)

    enum class LoadingStateAds {
        SUCCESS, FAILED, NONE
    }
}