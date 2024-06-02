package com.example.esgithub

import android.app.Application
import com.example.esgithub.di.init

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        init(this)
    }
}
