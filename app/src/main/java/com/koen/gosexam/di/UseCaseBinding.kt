package com.koen.gosexam.di

import com.koen.gosexam.domain.exam.*
import com.koen.gosexam.domain.exam.result.GetResultsExamByIdUseCase
import com.koen.gosexam.domain.exam.result.GetResultsExamByIdUseCaseImpl
import com.koen.gosexam.domain.question.PrepareFalseAnswerUseCase
import com.koen.gosexam.domain.question.PrepareFalseAnswerUseCaseImpl
import com.koen.gosexam.domain.start.GetStartFirstAppUseCase
import com.koen.gosexam.domain.start.GetStartFirstAppUseCaseImpl
import com.koen.gosexam.domain.start.SaveStartFirstAppUseCase
import com.koen.gosexam.domain.start.SaveStartFirstAppUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseBinding {
    @Binds
    fun bindInitExamUseCase(useCase: GetExamUseCaseImpl): GetExamUseCase

    @Binds
    fun bindGetExamListUseCase(useCase: GetExamListUseCaseImpl): GetExamListUseCase

    @Binds
    fun bindPrepareFalseAnswerUseCase(userCase: PrepareFalseAnswerUseCaseImpl): PrepareFalseAnswerUseCase

    @Binds
    fun bindGenerateExamUseCase(useCase: GenerateExamUseCaseImpl): GenerateExamUseCase

    @Binds
    fun bindPrepareAnswerTestUseCase(useCase: PrepareAnswerTestUseCaseImpl): PrepareAnswerTestUseCase

    @Binds
    fun bindPrepareResultTestUseCase(useCase: PrepareResultTestUseCaseImpl): PrepareResultTestUseCase

    @Binds
    fun bindGetExamSizeUseCase(useCase: GetExamSizeUseCaseImpl): GetExamSizeUseCase

    @Binds
    fun bindGenerateRangeExamUseCase(useCase: GenerateRangeExamUseCaseImpl): GenerateRangeExamUseCase

    @Binds
    fun bindSaveTypeFaculty(useCase: SaveTypeFacultyImpl): SaveTypeFaculty

    @Binds
    fun bindGetTypeFaculty(useCase: GetTypeFacultyImpl): GetTypeFaculty

    @Singleton
    @Binds
    fun bindGetSizeExamFlowUseCase(useCase: GetSizeExamFlowUseCaseImpl): GetSizeExamFlowUseCase

    @Binds
    fun bindGetStartFirstAppUseCase(useCase: GetStartFirstAppUseCaseImpl): GetStartFirstAppUseCase

    @Binds
    fun bindSaveStartFirstAppUseCase(useCase: SaveStartFirstAppUseCaseImpl): SaveStartFirstAppUseCase

    @Binds
    fun bindGetResultExamByIdUseCase(useCase: GetResultsExamByIdUseCaseImpl): GetResultsExamByIdUseCase

    @Binds
    fun bindPrepareAnswerTestExamModelUseCase(useCase: PrepareAnswerTestExamModeUseCaseImpl): PrepareAnswerTestExamModeUseCase
}