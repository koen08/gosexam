package com.koen.gosexam.presentation.main

import com.koen.gosexam.presentation.base.UiState

data class MainUiState(
    val typeExam: TypeExam = TypeExam.PRACTICE,
    val buttonHelpers: ButtonHelpers = ButtonHelpers.NO_ACTIVE,
    val currentText: String = ""
) : UiState