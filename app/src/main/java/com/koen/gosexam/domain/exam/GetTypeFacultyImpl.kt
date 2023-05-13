package com.koen.gosexam.domain.exam

import com.koen.gosexam.domain.models.TypeFaculty
import javax.inject.Inject

class GetTypeFacultyImpl @Inject constructor(
    private val examRepository: ExamRepository
) : GetTypeFaculty{
    override suspend fun invoke(): TypeFaculty {
        return examRepository.getTypeFaculty()
    }
}