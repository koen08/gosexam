package com.koen.gosexam.domain.exam

import com.koen.gosexam.presentation.models.ExamUi

interface GenerateExamUseCase {
    suspend operator fun invoke(countQuestion: Int) : List<ExamUi>
}