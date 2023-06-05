package com.koen.gosexam.core

import android.content.res.Resources
import androidx.core.util.TimeUtils
import com.koen.gosexam.R
import java.util.concurrent.TimeUnit

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

    override val errorTextFacultySelection: String
        get() = resource.getString(R.string.error_facultySelection)

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

    override fun getTextInfoTest(size: String): String {
        return resource.getString(R.string.main_tvInfoText, size)
    }

    override fun getLastTime(time: String): String {
        return resource.getString(R.string.examTest_lastTime, time)
    }

    override fun getTimeTimeSpent(time: Long): String {
        val commonTime = 60_000 - time
        var minutes = TimeUnit.MILLISECONDS.toMinutes(commonTime)
        var seconds = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(minutes)
        if (minutes <= 0 && seconds <= 0) {
            minutes = 60
            seconds = 0
        }
        val result = String.format("%d:%02d", minutes, seconds)
        return resource.getString(R.string.examTest_spentTime, result)
    }

    override fun getBtnExamTest(isCheck: Boolean): String {
        return if (isCheck) {
            R.string.main_btnExamStart
        } else {
            R.string.main_btnExamEnd
        }.let {
            resource.getString(it)
        }
    }

    override fun getResultCountAnswer(countAnswerTrue: Int, commonAnswerTrue: Int): String {
        return resource.getString(
            R.string.examResult_countTest,
            countAnswerTrue.toString(),
            commonAnswerTrue.toString()
        )
    }

}