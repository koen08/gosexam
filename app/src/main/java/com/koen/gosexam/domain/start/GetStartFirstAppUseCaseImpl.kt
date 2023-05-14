package com.koen.gosexam.domain.start

import com.koen.gosexam.domain.exam.ExamRepository
import com.koen.gosexam.presentation.models.ExamUi
import javax.inject.Inject

class GetStartFirstAppUseCaseImpl @Inject constructor(
    private val examRepository: ExamRepository
) : GetStartFirstAppUseCase {
    override suspend fun invoke(): Boolean {
        return examRepository.getIsStartFirstApp()
    }
}