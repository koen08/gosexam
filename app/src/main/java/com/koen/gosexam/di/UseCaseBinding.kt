package com.koen.gosexam.di

import com.koen.gosexam.domain.exam.*
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

    @Binds
    fun bindGenerateExamUseCase(useCase : GenerateExamUseCaseImpl) : GenerateExamUseCase

    @Binds
    fun bindPrepareAnswerTestUseCase(useCase: PrepareAnswerTestUseCaseImpl) : PrepareAnswerTestUseCase

    @Binds
    fun bindPrepareResultTestUseCase(useCase: PrepareResultTestUseCaseImpl) : PrepareResultTestUseCase

    @Binds
    fun bindGetExamSizeUseCase(useCase: GetExamSizeUseCaseImpl) : GetExamSizeUseCase

    @Binds
    fun bindGenerateRangeExamUseCase(useCase: GenerateRangeExamUseCaseImpl): GenerateRangeExamUseCase
}