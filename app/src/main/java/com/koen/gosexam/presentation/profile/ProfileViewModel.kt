package com.koen.gosexam.presentation.profile

import com.koen.gosexam.presentation.base.BaseViewModel
import com.koen.gosexam.presentation.examlist.ExamListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(): BaseViewModel<ProfileUiState>() {
    override val _uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
    override val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()
}