package com.koen.gosexam.domain.exam

import com.koen.gosexam.domain.models.TypeFaculty
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSizeExamFlowUseCaseImpl @Inject constructor(
    private val examRepository: ExamRepository
) : GetSizeExamFlowUseCase {
    override fun invoke(): Flow<Int> {
        return examRepository.getExamSizeFlow()
    }
}