package com.koen.gosexam.domain.exam

import com.koen.gosexam.core.DrawableResource
import com.koen.gosexam.core.StringResource
import com.koen.gosexam.domain.models.mapToExamTestPresentation
import com.koen.gosexam.domain.models.mapToPresentation
import com.koen.gosexam.presentation.models.ExamUi
import javax.inject.Inject

class GenerateRangeExamUseCaseImpl @Inject constructor(
    private val examRepository: ExamRepository,
    private val stringResource: StringResource,
    private val drawableResource: DrawableResource
) : GenerateRangeExamUseCase {
    override suspend fun invoke(minIndex: Int, maxIndex: Int): List<ExamUi> {
        return examRepository.getExamByRange(minIndex, maxIndex)
            .mapToExamTestPresentation(stringResource, drawableResource)
    }
}