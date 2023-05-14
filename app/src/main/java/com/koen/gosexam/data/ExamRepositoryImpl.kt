package com.koen.gosexam.data

import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.koen.gosexam.data.local.entity.mapToDomain
import com.koen.gosexam.data.local.exams.ExamDao
import com.koen.gosexam.data.local.shared.SharedPrefString
import com.koen.gosexam.data.remote.datasource.ExamRemoteDataSource
import com.koen.gosexam.data.remote.dto.mapToEntityList
import com.koen.gosexam.domain.exam.ExamRepository
import com.koen.gosexam.domain.models.Exam
import com.koen.gosexam.domain.models.TypeFaculty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import javax.inject.Inject

class ExamRepositoryImpl @Inject constructor(
    private val examRemoteDataSource: ExamRemoteDataSource,
    private val examDao: ExamDao,
    private val sharedPrefString: SharedPrefString
) : ExamRepository {

    companion object {
        const val fileNameMedical = "exam.txt"
        const val fileNamePediator = "pediator.txt"
    }

    private val examSizeMutableStateFlow = MutableStateFlow(0)

    override suspend fun getExam() {
        val type = sharedPrefString.get()
        val fileName = if (type == TypeFaculty.MEDICAL.nameValue) {
            fileNameMedical
        } else fileNamePediator
        val exam = examRemoteDataSource.getExam(fileName)
        if (exam.isNotEmpty()) {
            examDao.deleteTable()
            examDao.resetAutoincrement()
            examDao.insert(exam.mapToEntityList())
            examSizeMutableStateFlow.emit(exam.size)
        }
    }

    override suspend fun getExamFromLocal(): List<Exam> {
        return examDao.getBonusCards().mapToDomain()
    }

    override suspend fun generateExam(countQuestion: Int): List<Exam> {
        return examDao.generateExamTest(
            count = countQuestion
        ).mapToDomain()
    }

    override suspend fun getSizeExam(): Int {
        return getExamFromLocal().size
    }

    override suspend fun getExamByRange(min: Int, max: Int): List<Exam> {
        return examDao.generateTestByRange(min, max).mapToDomain()
    }

    override suspend fun saveTypeFaculty(typeFaculty: TypeFaculty) {
        sharedPrefString.save(typeFaculty.nameValue)
    }

    override suspend fun getTypeFaculty(): TypeFaculty {
        val type = sharedPrefString.get()
        return if (type == TypeFaculty.MEDICAL.nameValue) {
            TypeFaculty.MEDICAL
        } else TypeFaculty.PEDIATOR
    }

    override fun getExamSizeFlow(): Flow<Int> {
        return examSizeMutableStateFlow
    }

}