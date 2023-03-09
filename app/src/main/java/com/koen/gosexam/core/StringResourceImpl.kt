package com.koen.gosexam.core

import android.content.res.Resources
import com.koen.gosexam.R

class StringResourceImpl(private val resource: Resources) : StringResource {
    override fun getNumberQuestion(number: String): String {
        return resource.getString(R.string.examList_questionNumber, number)
    }

}