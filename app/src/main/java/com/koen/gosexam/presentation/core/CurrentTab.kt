package com.koen.gosexam.presentation.core

enum class CurrentTab {
    FIRST, SECOND;

    companion object {
        fun getTab(position: Int) = if (position == 0) {
            FIRST
        } else SECOND

        fun CurrentTab.isFirst() = this == FIRST

        fun CurrentTab.isSecond() = this == SECOND
    }


}