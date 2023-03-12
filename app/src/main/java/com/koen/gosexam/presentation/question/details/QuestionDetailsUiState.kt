package com.koen.gosexam.presentation.question.details

import com.koen.gosexam.presentation.base.UiState
import com.koen.gosexam.presentation.models.ExamUi
import com.koen.gosexam.presentation.models.QuestionDetailsUi

data class QuestionDetailsUiState(val questionDetailsUi: QuestionDetailsUi = QuestionDetailsUi()) :
    UiState {
    val answerFalseColor get() = questionDetailsUi.answerFalseColor
    val answerFalseStyle get() = questionDetailsUi.answerFalseStyle
    val question get() = questionDetailsUi.question
    val answerFalseText get() = questionDetailsUi.answerFalseText
    val titleNumber get() = questionDetailsUi.titleNumber
    val answers get() = questionDetailsUi.answers
}