package com.koen.gosexam.domain.start

import com.koen.gosexam.domain.exam.ExamRepository
import javax.inject.Inject

class SaveStartFirstAppUseCaseImpl @Inject constructor(
    private val examRepository: ExamRepository
): SaveStartFirstAppUseCase {
    override suspend fun invoke() {
        examRepository.saveIsStartFirstApp()
    }
}