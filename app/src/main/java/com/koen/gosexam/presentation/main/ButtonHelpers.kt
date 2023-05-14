package com.koen.gosexam.presentation.main

enum class ButtonHelpers(val value: String) {
    MAX("1049"), HIGH("100"), MEDIUM("50"), MIN("10"), NO_ACTIVE("");

    companion object {
        fun getButtonHelpers(text: String, maxSize : Int): ButtonHelpers {
            if (maxSize.toString() == text) {
                return MAX
            }
            return when (text) {
                MAX.value -> {
                    MAX
                }
                HIGH.value -> {
                    HIGH
                }
                MEDIUM.value -> {
                    MEDIUM
                }
                MIN.value -> {
                    MIN
                }
                else -> {
                    NO_ACTIVE
                }
            }
        }
    }
}