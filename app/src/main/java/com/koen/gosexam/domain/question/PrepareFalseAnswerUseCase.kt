package com.koen.gosexam.domain.question

import com.koen.gosexam.presentation.models.QuestionUi
import com.koen.gosexam.presentation.models.QuestionDetailsUi

interface PrepareFalseAnswerUseCase {
    operator fun invoke(questionUi: QuestionUi) : QuestionDetailsUi
}