package com.koen.gosexam.domain.exam

import com.koen.gosexam.domain.models.TypeFaculty
import com.koen.gosexam.presentation.models.ExamUi
import com.koen.gosexam.presentation.models.ResultTestUi

interface SaveTypeFaculty {
    suspend operator fun invoke(
        typeFaculty: TypeFaculty
    )
}