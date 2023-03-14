package com.koen.gosexam.domain.exam

import com.koen.gosexam.presentation.models.QuestionUi

interface GetExamListUseCase {
    suspend operator fun invoke() : List<QuestionUi>
}