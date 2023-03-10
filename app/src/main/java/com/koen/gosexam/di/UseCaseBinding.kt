package com.koen.gosexam.di

import com.koen.gosexam.domain.exam.GetExamListUseCase
import com.koen.gosexam.domain.exam.GetExamListUseCaseImpl
import com.koen.gosexam.domain.exam.GetExamUseCase
import com.koen.gosexam.domain.exam.GetExamUseCaseImpl
import com.koen.gosexam.domain.question.PrepareFalseAnswerUseCase
import com.koen.gosexam.domain.question.PrepareFalseAnswerUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseBinding {
    @Binds
    fun bindInitExamUseCase(useCase: GetExamUseCaseImpl) : GetExamUseCase

    @Binds
    fun bindGetExamListUseCase(useCase: GetExamListUseCaseImpl) : GetExamListUseCase

    @Binds
    fun bindPrepareFalseAnswerUseCase(userCase: PrepareFalseAnswerUseCaseImpl) : PrepareFalseAnswerUseCase
}