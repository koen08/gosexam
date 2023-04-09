package com.koen.gosexam.domain.exam

import com.koen.gosexam.presentation.models.ExamUi

interface GenerateRangeExamUseCase {
    suspend operator fun invoke(minIndex: Int, maxIndex: Int) : List<ExamUi>
}