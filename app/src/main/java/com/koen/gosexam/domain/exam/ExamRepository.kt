package com.koen.gosexam.domain.exam

interface ExamRepository {
    suspend fun getExam()
}