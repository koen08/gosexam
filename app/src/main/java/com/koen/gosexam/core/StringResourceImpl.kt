package com.koen.gosexam.core

import android.content.res.Resources
import com.koen.gosexam.R

class StringResourceImpl(private val resource: Resources) : StringResource {

    override val btnNextText: String
        get() = resource.getString(R.string.common_next)

    override val btnCompleteText: String
        get() = resource.getString(R.string.common_complete)
    override fun getNumberQuestion(number: String): String {
        return resource.getString(R.string.examList_questionNumber, number)
    }

    override fun answerFalseText(answer: String, index: Int): String {
        return resource.getString(R.string.questionDetails_answerFalseText ,index, answer)
    }

    override fun positionFromCommon(index: Int, common: Int): String {
        return resource.getString(R.string.examTest_positionFromCommon, index.toString(), common.toString())
    }

    override fun getTextBtnCompleteOrNext(complete: Boolean): String {
        return if (complete) {
            btnCompleteText
        } else {
            btnNextText
        }
    }

}