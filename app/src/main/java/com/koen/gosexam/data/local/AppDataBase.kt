package com.koen.gosexam.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.koen.gosexam.data.local.entity.ExamEntity
import com.koen.gosexam.data.local.exams.ExamDao

@Database(entities = [ExamEntity::class], version = 1)
@TypeConverters(StringListConverts::class)
abstract class AppDataBase : RoomDatabase() {

    abstract val examDao : ExamDao

}