package com.koen.gosexam.domain.start

import com.koen.gosexam.presentation.models.ExamUi

interface GetStartFirstAppUseCase {
    suspend operator fun invoke() : Boolean
}