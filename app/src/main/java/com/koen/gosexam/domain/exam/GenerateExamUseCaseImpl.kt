package com.koen.gosexam.domain.exam

import com.koen.gosexam.core.DrawableResource
import com.koen.gosexam.core.StringResource
import com.koen.gosexam.di.IoDispatcher
import com.koen.gosexam.domain.models.mapToExamTestPresentation
import com.koen.gosexam.presentation.models.ExamUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GenerateExamUseCaseImpl @Inject constructor(
    private val examRepository: ExamRepository,
    private val stringResource: StringResource,
    private val drawableResource: DrawableResource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : GenerateExamUseCase {
    override suspend fun invoke(countQuestion: Int): List<ExamUi> {
        return withContext(dispatcher) {
            examRepository.generateExam(countQuestion)
                .mapToExamTestPresentation(stringResource, drawableResource)
        }
    }
}