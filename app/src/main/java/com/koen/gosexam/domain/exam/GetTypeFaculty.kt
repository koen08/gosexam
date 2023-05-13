package com.koen.gosexam.domain.exam

import com.koen.gosexam.domain.models.TypeFaculty

interface GetTypeFaculty {
    suspend operator fun invoke(): TypeFaculty
}