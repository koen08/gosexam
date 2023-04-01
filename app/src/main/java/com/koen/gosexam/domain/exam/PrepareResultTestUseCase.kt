package com.koen.gosexam.domain.exam

import com.koen.gosexam.presentation.models.AnswerTestUi
import com.koen.gosexam.presentation.models.ExamUi
import com.koen.gosexam.presentation.models.ResultTestUi

interface PrepareResultTestUseCase {
    suspend operator fun invoke(
        examUiList: List<ExamUi>
    ): ResultTestUi
}