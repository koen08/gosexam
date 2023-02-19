package com.koen.gosexam.data.remote.datasource

import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.koen.gosexam.data.remote.dto.ExamDto
import com.koen.gosexam.extension.empty
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.ByteArrayInputStream
import java.io.InputStreamReader
import javax.inject.Inject
import kotlin.coroutines.resume

class ExamRemoteDataSourceImpl @Inject constructor() : ExamRemoteDataSource {
    companion object {
        private const val INDEX_QUESTION = 0
        private const val INDEX_START_ANSWERS = 1
    }

    override suspend fun getExam(): List<ExamDto> {
        val examDtoList = mutableListOf<ExamDto>()
        return suspendCancellableCoroutine { continuation ->
            val storageRef = FirebaseStorage.getInstance().reference
            val child = storageRef.child("exam.txt")
            child.getBytes(10000).addOnSuccessListener {
                val inputStream = InputStreamReader(ByteArrayInputStream(it))
                val buffer = mutableListOf<String>()
                inputStream.readLines().forEachIndexed { index, line ->
                    if (line == "EXIT") return@forEachIndexed
                    if (line.isNotEmpty()) {
                        buffer.add(line)
                    } else if ((index + 1) % 6 == 0) {
                        examDtoList.add(
                            createFromQuestion(buffer)
                        )
                        buffer.clear()
                    }
                }
                if (continuation.isActive) continuation.resume(examDtoList)
            }.addOnFailureListener {
                Log.e("TAG1", it.message.toString())
                if (continuation.isActive) continuation.resume(examDtoList)
            }
        }
    }

    private fun createFromQuestion(blockQuestionItems: List<String>): ExamDto {
        val question = blockQuestionItems.getOrElse(INDEX_QUESTION) {
            String.empty
        }
        val answers = mutableListOf<String>()
        blockQuestionItems.forEachIndexed { index, s ->
            if (index > 0) answers.add(s)
        }
        return ExamDto(
            question = question,
            answers = answers
        )
    }
}