package com.koen.gosexam.domain.exam

import com.koen.gosexam.domain.models.TypeFaculty
import com.koen.gosexam.presentation.models.ExamUi
import com.koen.gosexam.presentation.models.ResultTestUi
import javax.inject.Inject

class SaveTypeFacultyImpl @Inject constructor(
    private val examRepository: ExamRepository
) : SaveTypeFaculty {
    override suspend fun invoke(typeFaculty: TypeFaculty) {
        examRepository.saveTypeFaculty(typeFaculty = typeFaculty)
    }
}