package com.koen.gosexam.domain.exam

import com.koen.gosexam.domain.models.TypeFaculty
import kotlinx.coroutines.flow.Flow

interface GetSizeExamFlowUseCase {
    operator fun invoke(): Flow<Int>
}