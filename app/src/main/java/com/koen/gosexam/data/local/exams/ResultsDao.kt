package com.koen.gosexam.data.local.exams

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.koen.gosexam.data.local.entity.ExamEntity
import com.koen.gosexam.data.local.entity.ResultExamEntity

@Dao
interface ResultsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ResultExamEntity) : Long

    @Query(
        "SELECT * FROM result_exam_table"
    )
    fun get(): List<ResultExamEntity>

    @Query(
        "SELECT * FROM result_exam_table WHERE id == :id"
    )
    fun getById(id: Long) : ResultExamEntity
}