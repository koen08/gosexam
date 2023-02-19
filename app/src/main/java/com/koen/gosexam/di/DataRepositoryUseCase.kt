package com.koen.gosexam.di

import com.koen.gosexam.data.ExamRepositoryImpl
import com.koen.gosexam.domain.exam.ExamRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface DataRepositoryUseCase {
    @Binds
    fun bindExamRepository(examRepository: ExamRepositoryImpl): ExamRepository
}