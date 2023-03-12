package com.koen.gosexam.core

import android.content.res.Resources
import com.koen.gosexam.R

class StringResourceImpl(private val resource: Resources) : StringResource {
    override fun getNumberQuestion(number: String): String {
        return resource.getString(R.string.examList_questionNumber, number)
    }

    override fun answerFalseText(answer: String, index: Int): String {
        return resource.getString(R.string.questionDetails_answerFalseText ,index, answer)
    }

}