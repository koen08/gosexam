package com.koen.gosexam.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.koen.gosexam.data.local.shared.SharedPrefString
import com.koen.gosexam.data.local.shared.StartFirstAppSharedPref
import com.koen.gosexam.data.local.shared.StartFirstAppSharedPrefImpl
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
    fun provideFaculty(@ApplicationContext context: Context) : SharedPreferences {
        return context.getSharedPreferences("type_faculty", MODE_PRIVATE)
    }

    @Provides
    fun provideFacultyType(sharedPreferences: SharedPreferences) : SharedPrefString {
        return TypeFacultySharedPref(sharedPreferences)
    }

    @Provides
    fun provideStartFirstAppShared(sharedPreferences: SharedPreferences) : StartFirstAppSharedPref {
        return StartFirstAppSharedPrefImpl(sharedPreferences)
    }
}