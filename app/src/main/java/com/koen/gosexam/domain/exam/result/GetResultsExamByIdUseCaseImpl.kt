package com.koen.gosexam.domain.exam.result

import com.koen.gosexam.core.ColorResource
import com.koen.gosexam.core.DrawableResource
import com.koen.gosexam.core.StringResource
import com.koen.gosexam.core.StyleResource
import com.koen.gosexam.domain.exam.ExamRepository
import com.koen.gosexam.presentation.models.ExamUi
import com.koen.gosexam.presentation.models.ResultTestUi
import com.koen.gosexam.presentation.models.mapToUi
import javax.inject.Inject

class GetResultsExamByIdUseCaseImpl @Inject constructor(
    private val stringResource: StringResource,
    private val colorResource: ColorResource,
    private val styleResource: StyleResource,
    private val drawableResource: DrawableResource,
    private val examRepository: ExamRepository,
) : GetResultsExamByIdUseCase {
    override suspend fun invoke(id: Long): ResultTestUi {
        return examRepository.getResultById(id = id).mapToUi(
            stringResource, colorResource, styleResource, drawableResource
        )
    }
}