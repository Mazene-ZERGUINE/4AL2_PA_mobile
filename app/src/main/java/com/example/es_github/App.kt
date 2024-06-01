package com.example.es_github

import android.app.Application
import com.example.es_github.di.init


class App: Application() {
    override fun onCreate() {
        super.onCreate()
        init(this)
    }
}