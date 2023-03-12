package com.koen.gosexam.core

interface StringResource {
    fun getNumberQuestion(number: String) : String

    fun answerFalseText(answer: String, index: Int) : String
}