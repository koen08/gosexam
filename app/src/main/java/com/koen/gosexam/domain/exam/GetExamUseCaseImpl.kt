package com.koen.gosexam.domain.exam

import javax.inject.Inject

class GetExamUseCaseImpl @Inject constructor(
    private val examRepository: ExamRepository
) : GetExamUseCase {
    override suspend fun invoke() {
        examRepository.getExam()
    }
}