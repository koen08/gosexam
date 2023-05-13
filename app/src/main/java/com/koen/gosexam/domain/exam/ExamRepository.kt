package com.koen.gosexam.domain.exam

import com.koen.gosexam.domain.models.Exam
import com.koen.gosexam.domain.models.TypeFaculty

interface ExamRepository {
    suspend fun getExam()

    suspend fun getExamFromLocal() : List<Exam>

    suspend fun generateExam(countQuestion: Int) : List<Exam>

    suspend fun getSizeExam() : Int

    suspend fun getExamByRange(min: Int, max: Int) : List<Exam>

    suspend fun saveTypeFaculty(typeFaculty: TypeFaculty)

    suspend fun getTypeFaculty() : TypeFaculty
}