package com.koen.gosexam.presentation.models.uiEvent

import com.koen.gosexam.presentation.models.ExamUi
import com.koen.gosexam.presentation.models.QuestionUi
import com.koen.gosexam.presentation.models.base.UiEvent

data class OpenDetailsQuestion(val questionUi: QuestionUi) : UiEvent

data class OpenExamTest(val examUiList: List<ExamUi>) : UiEvent