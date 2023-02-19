package com.koen.gosexam.presentation.base

import androidx.lifecycle.ViewModel
import com.koen.gosexam.presentation.dialog.info.InfoDialogModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel<UiStateModel: UiState> : ViewModel() {
    protected abstract val _uiState: MutableStateFlow<UiStateModel>
    abstract val uiState: StateFlow<UiStateModel>

    protected val _infoDialogSharedFlow = MutableSharedFlow<InfoDialogModel>()
    val infoDialogSharedFlow = _infoDialogSharedFlow.asSharedFlow()
}