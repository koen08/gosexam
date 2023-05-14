package com.koen.gosexam.presentation.main

import com.koen.gosexam.presentation.base.UiState
import com.koen.gosexam.presentation.core.CurrentTab

data class MainUiState(
    val typeExam: TypeExam = TypeExam.PRACTICE,
    val buttonHelpers: ButtonHelpers = ButtonHelpers.NO_ACTIVE,
    val currentText: String = "",
    val currentTab: CurrentTab = CurrentTab.FIRST,
    val examSize: Int = 0,
    val startRange : Int = 0,
    val endRange: Int = 0,
    val textInfoTest : String = ""
) : UiState