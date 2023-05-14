package com.koen.gosexam.presentation.start

import androidx.lifecycle.viewModelScope
import com.koen.gosexam.core.StringResource
import com.koen.gosexam.domain.exam.SaveTypeFaculty
import com.koen.gosexam.domain.models.TypeFaculty
import com.koen.gosexam.domain.models.TypeFaculty.Companion.isNone
import com.koen.gosexam.domain.start.SaveStartFirstAppUseCase
import com.koen.gosexam.presentation.base.BaseViewModel
import com.koen.gosexam.presentation.models.uiEvent.ErrorUiEvent
import com.koen.gosexam.presentation.models.uiEvent.Finish
import com.koen.gosexam.presentation.profile.ProfileUiState
import com.koen.gosexam.presentation.profile.SelectionFacultyUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectionFacultyViewModel @Inject constructor(
    private val saveTypeFacultyUseCase: SaveTypeFaculty,
    private val stringResource: StringResource,
    private val saveStartFirstAppUseCase: SaveStartFirstAppUseCase
): BaseViewModel<SelectionFacultyUiState>() {
    override val _uiState = MutableStateFlow(SelectionFacultyUiState())
    override val uiState: StateFlow<SelectionFacultyUiState> = _uiState.asStateFlow()

    fun saveTypeFaculty() {
        if (uiState.value.typeFaculty.isNone) {
            sendEvent(ErrorUiEvent(
                stringResource.errorTextFacultySelection
            ))
            return
        }
        viewModelScope.launch {
            saveTypeFacultyUseCase(uiState.value.typeFaculty)
            saveFirstApp()
            sendEvent(Finish)
        }
    }

    fun updateTypeFaculty(typeFaculty: TypeFaculty) {
        _uiState.update { state ->
            state.copy(
                typeFaculty = typeFaculty
            )
        }
    }

    private fun saveFirstApp() {
        viewModelScope.launch {
            saveStartFirstAppUseCase()
        }
    }
}