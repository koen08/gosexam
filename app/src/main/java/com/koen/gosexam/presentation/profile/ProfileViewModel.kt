package com.koen.gosexam.presentation.profile

import androidx.lifecycle.viewModelScope
import com.koen.gosexam.domain.exam.GetExamListUseCase
import com.koen.gosexam.domain.exam.GetExamUseCase
import com.koen.gosexam.domain.exam.GetTypeFaculty
import com.koen.gosexam.domain.exam.SaveTypeFaculty
import com.koen.gosexam.domain.models.TypeFaculty
import com.koen.gosexam.presentation.base.BaseViewModel
import com.koen.gosexam.presentation.examlist.ExamListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val saveTypeFacultyUseCase: SaveTypeFaculty,
    private val getTypeFacultyUseCase: GetTypeFaculty,
    private val getExamUseCase: GetExamUseCase
) : BaseViewModel<ProfileUiState>() {
    override val _uiState = MutableStateFlow(ProfileUiState())
    override val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        getTypeFaculty()
    }

    fun saveTypeFaculty(typeFaculty: TypeFaculty) {
        _uiState.update { state ->
            state.copy(
                typeFaculty = typeFaculty
            )
        }
        viewModelScope.launch {
            saveTypeFacultyUseCase(typeFaculty)
            fetchExam()
        }
    }

    private suspend fun fetchExam() {
        getExamUseCase()
    }

    private fun getTypeFaculty() {
        viewModelScope.launch {
            _uiState.update { state ->
                state.copy(
                    typeFaculty = getTypeFacultyUseCase()
                )
            }
        }
    }
}