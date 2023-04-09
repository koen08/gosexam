package com.koen.gosexam.data.local.exams

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.koen.gosexam.data.local.entity.ExamEntity

@Dao
interface ExamDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(items: List<ExamEntity>)

    @Query("DELETE FROM exam_table")
    fun deleteTable()

    @Query("""
        SELECT * FROM exam_table
    """)
    fun getBonusCards() : List<ExamEntity>

    @Query(
        "SELECT * FROM exam_table ORDER BY RANDOM() LIMIT :count"
    )
    fun generateExamTest(count : Int) : List<ExamEntity>

    @Query(
        "SELECT * FROM exam_table WHERE id >= :min AND id <= :max"
    )
    fun generateTestByRange(min: Int, max: Int) : List<ExamEntity>

    @Query("DELETE FROM sqlite_sequence WHERE name = 'exam_table'")
    fun resetAutoincrement()
}