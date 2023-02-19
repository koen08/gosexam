package com.koen.gosexam.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exam_table")
data class ExamEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "question")
    val question: String = "",
    @ColumnInfo(name = "answers")
    val answers: List<String> = emptyList()
)