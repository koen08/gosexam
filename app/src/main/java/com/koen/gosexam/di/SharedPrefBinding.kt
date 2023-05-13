package com.koen.gosexam.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.koen.gosexam.data.local.shared.SharedPrefString
import com.koen.gosexam.data.local.shared.TypeFacultySharedPref
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SharedPrefBinding {
    @Provides
    fun provideFacultyType(@ApplicationContext context: Context) : SharedPrefString {
        return TypeFacultySharedPref(context.getSharedPreferences("type_faculty", MODE_PRIVATE))
    }
}