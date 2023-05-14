package com.koen.gosexam.data.local.shared

import android.content.SharedPreferences
import com.koen.gosexam.domain.models.TypeFaculty
import javax.inject.Inject

class StartFirstAppSharedPrefImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
): StartFirstAppSharedPref {

    companion object {
        const val KEY = "start"
    }


    override fun save(obj: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(KEY, obj)
            apply()
        }
    }

    override fun get(): Boolean {
        return if (sharedPreferences.contains(KEY)) {
            sharedPreferences.getBoolean(KEY, true)
        } else {
            true
        }
    }
}