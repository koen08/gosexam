package com.koen.gosexam.presentation.exam

import com.koen.gosexam.presentation.base.UiState
import com.koen.gosexam.presentation.exam.ExamTestUiState.ExamMode.Companion.isExam
import com.koen.gosexam.presentation.models.ExamUi
import com.koen.gosexam.presentation.models.SettingsExam

data class ExamTestUiState(
    val examUiList: List<ExamUi> = emptyList(),
    val examUiListWithExamMode: List<ExamUi> = emptyList(),
    val settingsExam: SettingsExam,
    val currentPosition: Int = 0,
    val btnText: String = "",
    val clickableAnswers: Boolean = true,
    val isLoadSuccessAds: LoadingStateAds = LoadingStateAds.NONE,
    val examMode : ExamMode = ExamMode.WORKOUT,
) : UiState {
    val currentExam get() = examUiList.getOrNull(currentPosition)

    val examList
        get() = if (examMode.isExam()) {
            examUiListWithExamMode
        } else examUiList

    enum class LoadingStateAds {
        SUCCESS, FAILED, NONE
    }

    enum class ExamMode {
        WORKOUT, EXAM;

        companion object {
            fun getExamMode(isExam: Boolean) = if (isExam) {
                EXAM
            } else WORKOUT

            fun ExamMode.isExam() = this == EXAM
        }
    }
}