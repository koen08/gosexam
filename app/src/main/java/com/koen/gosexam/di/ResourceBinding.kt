package com.koen.gosexam.di

import android.content.Context
import com.koen.gosexam.core.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ResourceBinding {
    @Provides
    fun stringResourceProvide(@ApplicationContext context: Context) : StringResource {
        return StringResourceImpl(context.resources)
    }

    @Provides
    fun colorResourceProvide(@ApplicationContext context: Context) : ColorResource {
        return ColorResourceImpl(context)
    }

    @Provides
    fun styleResourceProvide() : StyleResource {
        return StyleResourceImpl()
    }
}