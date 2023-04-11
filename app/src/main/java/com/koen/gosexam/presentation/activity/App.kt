package com.koen.gosexam.presentation.activity

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.yandex.mobile.ads.common.InitializationListener
import com.yandex.mobile.ads.common.MobileAds
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(applicationContext)
        MobileAds.initialize(applicationContext
        ) { Log.i("YANDEX:INIT:", "Completed") }
    }
}