package com.koen.gosexam.domain.exam

import com.koen.gosexam.presentation.models.ExamUi

interface GetExamSizeUseCase {
    suspend operator fun invoke() : Int
}