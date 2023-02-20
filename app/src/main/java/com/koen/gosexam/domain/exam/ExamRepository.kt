package com.koen.gosexam.domain.exam

import com.koen.gosexam.domain.models.Exam

interface ExamRepository {
    suspend fun getExam()

    suspend fun getExamFromLocal() : List<Exam>
}