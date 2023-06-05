package com.koen.gosexam.data.local

import androidx.room.TypeConverter
import com.koen.gosexam.presentation.models.ResultExamUi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class StringListConverts {
    @TypeConverter
    fun fromList(value: List<String>): String = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String) : List<String> = Json.decodeFromString(value)
}

class ResultExamEntityConverter {
    @TypeConverter
    fun fromList(value: List<ResultExamUi>) : String = Json.encodeToString(value)

    @TypeConverter
    fun toList(value: String) : List<ResultExamUi> = Json.decodeFromString(value)
}