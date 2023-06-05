package com.koen.gosexam.domain.exam

import com.koen.gosexam.core.DrawableResource
import com.koen.gosexam.presentation.models.AnswerTestUi
import com.koen.gosexam.presentation.models.ExamUi
import javax.inject.Inject

class PrepareAnswerTestExamModeUseCaseImpl @Inject constructor(
    private val drawableResource: DrawableResource
) : PrepareAnswerTestExamModeUseCase {
    override suspend fun invoke(
        answerSelected: AnswerTestUi,
        examSelected: ExamUi,
        examUiList: List<ExamUi>
    ): List<ExamUi> {
        val newList = examUiList.toList()
        val examUi = newList.find { it.question == examSelected.question }
        val answersNew = examUi?.answers?.map {
            val isSelected = it.text == answerSelected.text
            it.copy(
                selected = isSelected,
                backgroundSelected = drawableResource.getSelectedPrimary80ElseGray60(isSelected)
            )
        }
        return newList.map {
            if (it.question == examUi?.question) {
                it.copy(
                    answers = answersNew ?: emptyList()
                )
            } else {
                it.copy()
            }
        }
    }
}