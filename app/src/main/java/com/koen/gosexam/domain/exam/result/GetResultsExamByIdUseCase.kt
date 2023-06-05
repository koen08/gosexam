package com.koen.gosexam.domain.exam.result

import com.koen.gosexam.presentation.models.ResultTestUi

interface GetResultsExamByIdUseCase {
    suspend operator fun invoke(id: Long) : ResultTestUi
}