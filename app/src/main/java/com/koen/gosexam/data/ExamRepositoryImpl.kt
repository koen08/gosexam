package com.koen.gosexam.data

import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.koen.gosexam.data.local.exams.ExamDao
import com.koen.gosexam.data.remote.datasource.ExamRemoteDataSource
import com.koen.gosexam.data.remote.dto.mapToEntityList
import com.koen.gosexam.domain.exam.ExamRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import javax.inject.Inject

class ExamRepositoryImpl @Inject constructor(
    private val examRemoteDataSource: ExamRemoteDataSource,
    private val examDao: ExamDao
) : ExamRepository {
    override suspend fun getExam() {
        val exam = examRemoteDataSource.getExam()
        examDao.insert(exam.mapToEntityList())
    }

}