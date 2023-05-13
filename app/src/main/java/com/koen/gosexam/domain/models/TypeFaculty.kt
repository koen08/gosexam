package com.koen.gosexam.domain.models

enum class TypeFaculty(val nameValue: String) {
    MEDICAL("MEDICAL"), PEDIATOR("PEDIATOR");

    companion object {
        val TypeFaculty.isMedical get() = this == MEDICAL

        val TypeFaculty.isPediator get() = this == PEDIATOR
    }
}