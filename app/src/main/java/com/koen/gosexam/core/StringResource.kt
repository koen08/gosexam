package com.koen.gosexam.core

interface StringResource {
    val btnCompleteText: String

    val btnNextText: String
    fun getNumberQuestion(number: String): String

    fun answerFalseText(answer: String, index: Int): String

    fun positionFromCommon(index: Int, common: Int): String

    fun getTextBtnCompleteOrNext(complete: Boolean) : String
}