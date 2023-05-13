package com.koen.gosexam.presentation.profile

import com.koen.gosexam.domain.models.TypeFaculty
import com.koen.gosexam.domain.models.TypeFaculty.Companion.isMedical
import com.koen.gosexam.presentation.base.UiState

data class ProfileUiState(
    val typeFaculty: TypeFaculty = TypeFaculty.MEDICAL
) : UiState {
}