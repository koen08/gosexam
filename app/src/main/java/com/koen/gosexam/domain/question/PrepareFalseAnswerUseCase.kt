package com.koen.gosexam.domain.question

import com.koen.gosexam.presentation.models.ExamUi
import com.koen.gosexam.presentation.models.QuestionDetailsUi

interface PrepareFalseAnswerUseCase {
    operator fun invoke(examUi: ExamUi) : QuestionDetailsUi
}