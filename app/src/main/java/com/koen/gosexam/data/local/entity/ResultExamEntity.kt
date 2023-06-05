package com.koen.gosexam.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.koen.gosexam.presentation.models.ResultExamUi

@Entity(tableName = "result_exam_table")
data class ResultExamEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "count_true_answer")
    val countTrueAnswer: Int = 0,
    @ColumnInfo(name = "is_success")
    val isSuccess: Boolean = false,
    @ColumnInfo(name = "common_answer")
    val commonAnswer: Int = 0,
    @ColumnInfo(name = "exam_list")
    val examList: List<ResultExamUi> = emptyList()
)