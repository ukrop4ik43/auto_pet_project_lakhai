package com.petprojject.autolakhai

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AutoApp : Application(){
    override fun onCreate() {
        FirebaseApp.initializeApp(applicationContext)
        super.onCreate()
    }
}