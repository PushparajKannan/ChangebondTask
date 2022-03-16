package com.example.changebondtask.utilities

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ChangebondApplication  : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}