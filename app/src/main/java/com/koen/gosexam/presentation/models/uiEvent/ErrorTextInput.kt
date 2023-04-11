package com.koen.gosexam.presentation.models.uiEvent

import com.koen.gosexam.presentation.base.UiState
import com.koen.gosexam.presentation.models.base.UiEvent

data class ErrorTextInput(
    val error: String
) : UiEvent