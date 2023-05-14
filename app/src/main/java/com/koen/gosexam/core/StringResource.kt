package com.koen.gosexam.core

interface StringResource {
    val btnCompleteText: String

    val btnNextText: String

    val resultTitleSuccess: String

    val resultTitleFail: String

    val errorEmptyTextInput: String

    val errorLarge: String
    fun getNumberQuestion(number: String): String

    fun answerFalseText(answer: String, index: Int): String

    fun positionFromCommon(index: Int, common: Int): String

    fun getTextBtnCompleteOrNext(complete: Boolean): String

    fun getTextTitleResult(countAnswerTrue: Int, commonAnswerTrue: Int): String

    fun getResultCountAnswer(countAnswerTrue: Int, commonAnswerTrue: Int): String

    fun getTextInfoTest(size: String) : String
}