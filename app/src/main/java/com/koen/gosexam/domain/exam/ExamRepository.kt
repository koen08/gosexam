package com.koen.gosexam.domain.exam

import com.koen.gosexam.data.local.entity.ResultExamEntity
import com.koen.gosexam.domain.models.Exam
import com.koen.gosexam.domain.models.TypeFaculty
import com.koen.gosexam.presentation.models.ResultTestUi
import kotlinx.coroutines.flow.Flow

interface ExamRepository {
    suspend fun getExam()

    suspend fun getExamFromLocal(): List<Exam>

    suspend fun generateExam(countQuestion: Int): List<Exam>

    suspend fun getSizeExam(): Int

    suspend fun getExamByRange(min: Int, max: Int): List<Exam>

    suspend fun getExamByRangeRandom(min: Int, max: Int) : List<Exam>

    suspend fun saveTypeFaculty(typeFaculty: TypeFaculty)

    suspend fun getTypeFaculty(): TypeFaculty

    fun getExamSizeFlow(): Flow<Int>

    suspend fun getIsStartFirstApp(): Boolean

    suspend fun saveIsStartFirstApp()

    suspend fun saveResults(result: ResultExamEntity) : Long

    suspend fun getResultById(id: Long) : ResultExamEntity
}