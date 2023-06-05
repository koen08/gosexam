package com.koen.gosexam.domain.exam

import com.koen.gosexam.presentation.models.AnswerTestUi
import com.koen.gosexam.presentation.models.ExamUi

interface PrepareAnswerTestExamModeUseCase {
    suspend operator fun invoke(
        answerSelected: AnswerTestUi,
        examSelected: ExamUi,
        examUiList: List<ExamUi>
    ): List<ExamUi>
}