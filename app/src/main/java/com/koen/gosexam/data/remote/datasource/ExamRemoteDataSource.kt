package com.koen.gosexam.data.remote.datasource

import com.koen.gosexam.data.remote.dto.ExamDto

interface ExamRemoteDataSource {
    suspend fun getExam() : List<ExamDto>
}