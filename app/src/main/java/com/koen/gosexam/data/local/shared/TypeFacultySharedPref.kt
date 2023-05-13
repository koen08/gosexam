package com.koen.gosexam.data.local.shared

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.koen.gosexam.domain.models.TypeFaculty
import javax.inject.Inject

class TypeFacultySharedPref @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : SharedPrefString {

    companion object {
        const val KEY = "type"
    }

    override fun save(obj: String) {
        sharedPreferences.edit().apply {
            putString(KEY, obj)
            apply()
        }

    }

    override fun clear() {
        sharedPreferences.edit().apply {
            remove(KEY)
            clear()
            apply()
        }
    }

    override fun get(): String {
        return if (sharedPreferences.contains(KEY)) {
            sharedPreferences.getString(KEY, TypeFaculty.MEDICAL.nameValue)
                ?: TypeFaculty.MEDICAL.nameValue
        } else {
            TypeFaculty.MEDICAL.nameValue
        }
    }

}