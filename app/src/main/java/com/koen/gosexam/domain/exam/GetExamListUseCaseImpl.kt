package com.koen.gosexam.domain.exam

import com.koen.gosexam.core.StringResource
import com.koen.gosexam.di.IoDispatcher
import com.koen.gosexam.domain.models.mapToPresentation
import com.koen.gosexam.presentation.models.QuestionUi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetExamListUseCaseImpl @Inject constructor(
    private val examRepository: ExamRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val stringResource: StringResource
) : GetExamListUseCase {
    override suspend fun invoke(): List<QuestionUi> {
        return withContext(dispatcher) {
            val examList = examRepository.getExamFromLocal()
            examList.mapToPresentation(stringResource)
        }
    }
}