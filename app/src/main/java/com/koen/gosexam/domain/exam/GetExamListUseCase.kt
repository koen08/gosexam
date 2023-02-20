package com.koen.gosexam.domain.exam

import com.koen.gosexam.presentation.models.ExamUi

interface GetExamListUseCase {
    suspend operator fun invoke() : List<ExamUi>
}