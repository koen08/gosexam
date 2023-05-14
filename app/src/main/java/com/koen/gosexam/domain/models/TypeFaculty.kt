package com.koen.gosexam.domain.models

enum class TypeFaculty(val nameValue: String) {
    MEDICAL("MEDICAL"), PEDIATOR("PEDIATOR"), NONE("NONE");

    companion object {
        val TypeFaculty.isMedical get() = this == MEDICAL

        val TypeFaculty.isPediator get() = this == PEDIATOR

        val TypeFaculty.isNone get() = this == NONE
    }
}