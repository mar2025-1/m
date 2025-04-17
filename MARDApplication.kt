package com.example.mard

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import androidx.work.WorkManager
import com.google.firebase.FirebaseApp
import timber.log.Timber

@HiltAndroidApp
class MARDApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
        initFirebase()
        initWorkManager()
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initFirebase() {
        FirebaseApp.initializeApp(this)
    }

    private fun initWorkManager() {
        WorkManager.initialize(
            this,
            androidx.work.Configuration.Builder()
                .setMinimumLoggingLevel(android.util.Log.INFO)
                .build()
        )
    }
}