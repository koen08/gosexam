package com.koen.gosexam.domain.exam

import com.koen.gosexam.di.IoDispatcher
import com.koen.gosexam.domain.models.mapToPresentation
import com.koen.gosexam.presentation.models.ExamUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetExamListUseCaseImpl @Inject constructor(
    private val examRepository: ExamRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : GetExamListUseCase {
    override suspend fun invoke(): List<ExamUi> {
        return withContext(dispatcher) {
            val examList = examRepository.getExamFromLocal()
            examList.mapToPresentation()
        }
    }
}