package com.koen.gosexam.domain.exam

import com.koen.gosexam.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetExamUseCaseImpl @Inject constructor(
    private val examRepository: ExamRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : GetExamUseCase {
    override suspend fun invoke() {
        withContext(dispatcher) {
            examRepository.getExam()
        }
    }
}