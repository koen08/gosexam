package com.koen.gosexam.domain.exam

import com.koen.gosexam.presentation.models.ExamUi
import javax.inject.Inject

class GetExamSizeUseCaseImpl @Inject constructor(
    private val examRepository: ExamRepository
) : GetExamSizeUseCase {
    override suspend fun invoke(): Int {
        return examRepository.getSizeExam()
    }
}