package com.koen.gosexam.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.koen.gosexam.data.local.entity.ExamEntity
import com.koen.gosexam.data.local.entity.ResultExamEntity
import com.koen.gosexam.data.local.exams.ExamDao
import com.koen.gosexam.data.local.exams.ResultsDao

@Database(entities = [ExamEntity::class, ResultExamEntity::class], version = 3, exportSchema = false)
@TypeConverters(StringListConverts::class, ResultExamEntityConverter::class)
abstract class AppDataBase : RoomDatabase() {

    abstract val examDao : ExamDao

    abstract val resultsDao: ResultsDao

}