package com.koen.gosexam.di

import com.koen.gosexam.data.remote.datasource.ExamRemoteDataSource
import com.koen.gosexam.data.remote.datasource.ExamRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RemoteDataSourceBinding {
    @Binds
    fun bindExamRds(rds: ExamRemoteDataSourceImpl): ExamRemoteDataSource
}