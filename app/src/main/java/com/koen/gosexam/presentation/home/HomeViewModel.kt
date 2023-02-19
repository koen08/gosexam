package com.koen.gosexam.presentation.home

import com.koen.gosexam.presentation.base.BaseViewModel
import com.koen.gosexam.presentation.main.MainUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): BaseViewModel<HomeUiState>() {
    override val _uiState: MutableStateFlow<HomeUiState>
        get() = MutableStateFlow(HomeUiState())
    override val uiState: StateFlow<HomeUiState>
        get() = _uiState.asStateFlow()
}