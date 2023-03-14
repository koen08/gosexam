package com.koen.gosexam.domain.question

import com.koen.gosexam.core.ColorResource
import com.koen.gosexam.core.StringResource
import com.koen.gosexam.core.StyleResource
import com.koen.gosexam.presentation.models.QuestionUi
import com.koen.gosexam.presentation.models.QuestionDetailsUi
import javax.inject.Inject

class PrepareFalseAnswerUseCaseImpl @Inject constructor(
    private val colorResource: ColorResource,
    private val styleResource: StyleResource,
    private val stringResource: StringResource
) : PrepareFalseAnswerUseCase {
    companion object {
        const val INDEX_FIRST_FALSE_ANSWER = 1
    }

    override fun invoke(questionUi: QuestionUi): QuestionDetailsUi {
        return QuestionDetailsUi(
            id = questionUi.id,
            question = questionUi.question,
            answers = questionUi.answers,
            titleNumber = questionUi.numberQuestion,
            answerFalseStyle = styleResource.getDefaultBody,
            answerFalseColor = colorResource.gray100,
            answerFalseText = questionUi.answers.drop(INDEX_FIRST_FALSE_ANSWER)
                .mapIndexed { index, answer -> stringResource.answerFalseText(answer, index + 1) }
        )
    }
}