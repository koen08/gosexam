package com.koen.gosexam.di

import android.content.Context
import androidx.room.Room
import com.koen.gosexam.data.local.AppDataBase
import com.koen.gosexam.data.local.exams.ExamDao
import com.koen.gosexam.data.local.exams.ResultsDao
import com.koen.gosexam.presentation.activity.App
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseBinding {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDataBase {
        return Room
            .databaseBuilder(appContext, AppDataBase::class.java, "bau_center_database.db")
            .fallbackToDestructiveMigration() //todo add Migrations when release is coming
            .build()
    }

    @Provides
    @Singleton
    fun provideExamDao(appDataBase: AppDataBase): ExamDao {
        return appDataBase.examDao
    }

    @Provides
    @Singleton
    fun provideResultDao(appDataBase: AppDataBase) : ResultsDao {
        return appDataBase.resultsDao
    }
}