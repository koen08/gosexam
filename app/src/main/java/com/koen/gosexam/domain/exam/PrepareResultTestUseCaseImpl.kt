package com.koen.gosexam.domain.exam

import com.koen.gosexam.core.ColorResource
import com.koen.gosexam.core.DrawableResource
import com.koen.gosexam.core.StringResource
import com.koen.gosexam.core.StyleResource
import com.koen.gosexam.presentation.models.ExamUi
import com.koen.gosexam.presentation.models.ResultTestUi
import com.koen.gosexam.presentation.models.mapToResult
import javax.inject.Inject

class PrepareResultTestUseCaseImpl @Inject constructor(
    private val stringResource: StringResource,
    private val colorResource: ColorResource,
    private val styleResource: StyleResource,
    private val drawableResource: DrawableResource
) : PrepareResultTestUseCase {
    override suspend fun invoke(examUiList: List<ExamUi>): ResultTestUi {
        return examUiList.mapToResult(stringResource, colorResource, styleResource, drawableResource)
    }
}