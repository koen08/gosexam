package com.koen.gosexam.extension

import androidx.lifecycle.SavedStateHandle

fun <T> SavedStateHandle.getOrDefault(key: String, default: () -> T): T {
    return this.get<T>(key) ?: default()
}

fun <T> SavedStateHandle.getListOrEmpty(key: String) : List<T> {
    return this.get<List<T>>(key) ?: emptyList()
}