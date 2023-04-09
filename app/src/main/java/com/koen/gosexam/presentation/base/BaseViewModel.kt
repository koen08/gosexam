package com.koen.gosexam.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koen.gosexam.presentation.dialog.info.InfoDialogModel
import com.koen.gosexam.presentation.models.base.UiEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<UiStateModel: UiState> : ViewModel() {
    protected abstract val _uiState: MutableStateFlow<UiStateModel>
    abstract val uiState: StateFlow<UiStateModel>

    protected val _infoDialogSharedFlow = MutableSharedFlow<InfoDialogModel>()
    val infoDialogSharedFlow = _infoDialogSharedFlow.asSharedFlow()

    protected open val _uiEvent: Channel<UiEvent> = Channel()
    open val uiEvent: Flow<UiEvent> by lazy { _uiEvent.receiveAsFlow() }

    /**
     * Применяется для fragments которые имеют общую view model.
     * Использование _uiEventShared отправляет uiEvent каждому fragment
     * */
    protected val _uiEventShared: MutableSharedFlow<UiEvent> = MutableSharedFlow(replay = 1)
    open val uiEventShared: Flow<UiEvent> by lazy {
        _uiEventShared
    }


    fun sendEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(uiEvent)
        }
    }

    fun sendEventShared(uiEvent: UiEvent) {
        viewModelScope.launch {
            _uiEventShared.emit(uiEvent)
        }
    }
}