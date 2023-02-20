package com.koen.gosexam.data.remote.dto

import com.koen.gosexam.data.local.entity.ExamEntity
import com.koen.gosexam.domain.models.Exam

data class ExamDto(
    val question: String,
    val answers: List<String>
)

fun ExamDto.mapToDomain() = Exam(
    question = question,
    answers = answers,
    id = 0
)

fun List<ExamDto>.mapToEntityList(): List<ExamEntity> = this.map {
    ExamEntity(
        question = it.question,
        answers = it.answers
    )
}