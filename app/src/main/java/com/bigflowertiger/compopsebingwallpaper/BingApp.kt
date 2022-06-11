package com.bigflowertiger.compopsebingwallpaper

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BingApp : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: BingApp

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        context = applicationContext

    }
}