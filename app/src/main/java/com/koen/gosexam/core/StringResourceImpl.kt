package com.koen.gosexam.core

import android.content.res.Resources
import com.koen.gosexam.R

class StringResourceImpl(private val resource: Resources) : StringResource {

    override val btnNextText: String
        get() = resource.getString(R.string.common_next)

    override val btnCompleteText: String
        get() = resource.getString(R.string.common_complete)

    override val resultTitleFail: String
        get() = resource.getString(R.string.examResult_failTest)

    override val resultTitleSuccess: String
        get() = resource.getString(R.string.examResult_successTest)

    override val errorEmptyTextInput: String
        get() = resource.getString(R.string.common_emptyError)

    override val errorLarge: String
        get() = resource.getString(R.string.common_errorLarge)

            override fun getNumberQuestion(number: String): String {
        return resource.getString(R.string.examList_questionNumber, number)
    }

    override fun answerFalseText(answer: String, index: Int): String {
        return resource.getString(R.string.questionDetails_answerFalseText, index, answer)
    }

    override fun positionFromCommon(index: Int, common: Int): String {
        return resource.getString(
            R.string.examTest_positionFromCommon,
            index.toString(),
            common.toString()
        )
    }

    override fun getTextBtnCompleteOrNext(complete: Boolean): String {
        return if (complete) {
            btnCompleteText
        } else {
            btnNextText
        }
    }

    override fun getTextTitleResult(countAnswerTrue: Int, commonAnswerTrue: Int): String {
        val result = countAnswerTrue.toFloat() / commonAnswerTrue.toFloat()
        return if (result >= 0.7) {
            resultTitleSuccess
        } else resultTitleFail
    }

    override fun getResultCountAnswer(countAnswerTrue: Int, commonAnswerTrue: Int): String {
        return resource.getString(
            R.string.examResult_countTest,
            countAnswerTrue.toString(),
            commonAnswerTrue.toString()
        )
    }

}