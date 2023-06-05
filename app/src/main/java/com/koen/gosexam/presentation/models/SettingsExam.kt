package com.koen.gosexam.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class SettingsExam : Parcelable {
    data class RandomTest(
        val countQuestion: Int,
        val isExamMode: Boolean,
    ) : SettingsExam()

    data class RangeTest(
        val startRange: Int,
        val endRange: Int,
        val isRandomPosition: Boolean
    ) : SettingsExam()
}