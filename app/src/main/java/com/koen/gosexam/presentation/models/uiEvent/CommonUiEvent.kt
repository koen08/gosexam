package com.koen.gosexam.presentation.models.uiEvent

import com.koen.gosexam.presentation.models.base.UiEvent

data class RangeSliderInit(val min: Int, val max: Int) : UiEvent

data class RangeSliderValues(val min: Int, val max: Int) : UiEvent

object Loading : UiEvent

object DismissLoading: UiEvent