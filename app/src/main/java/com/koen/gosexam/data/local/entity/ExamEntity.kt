package com.koen.gosexam.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.koen.gosexam.domain.models.Exam

@Entity(tableName = "exam_table")
data class ExamEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "question")
    val question: String = "",
    @ColumnInfo(name = "answers")
    val answers: List<String> = emptyList()
)

fun List<ExamEntity>.mapToDomain() = map {
    Exam(
        id = it.id,
        question = it.question,
        answers = it.answers
    )
}