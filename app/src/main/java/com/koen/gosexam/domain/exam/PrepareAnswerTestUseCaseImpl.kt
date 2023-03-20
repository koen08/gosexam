package com.koen.gosexam.domain.exam

import com.koen.gosexam.core.DrawableResource
import com.koen.gosexam.presentation.models.AnswerTestUi
import com.koen.gosexam.presentation.models.ExamUi
import javax.inject.Inject

class PrepareAnswerTestUseCaseImpl @Inject constructor(
    private val drawableResource: DrawableResource
) : PrepareAnswerTestUseCase {
    override suspend fun invoke(
        answerSelected: AnswerTestUi,
        examSelected: ExamUi,
        examUiList: List<ExamUi>
    ): List<ExamUi> {
        return examUiList.map { exam ->
            if (examSelected.question == exam.question) {
                exam.copy(
                    answers = exam.answers.map { answer ->
                        if (answer.text == answerSelected.text) {
                            answer.copy(
                                selected = true,
                                backgroundSelected = drawableResource.getSelectedPrimary80ElseGray60(true)
                            )
                        } else {
                            answer.copy(
                                selected = false,
                                backgroundSelected = drawableResource.getSelectedPrimary80ElseGray60(false)
                            )
                        }
                    }
                )
            } else {
                exam.copy(
                    answers = exam.answers.map { answer ->
                        answer.copy(
                            selected = false,
                            backgroundSelected = drawableResource.getSelectedPrimary80ElseGray60(false)
                        )
                    }
                )
            }
        }
    }
}