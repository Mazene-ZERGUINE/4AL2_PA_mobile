package com.example.esgithub.di

import android.content.Context
import com.example.esgithub.di.modules.coreModule
import com.example.esgithub.di.modules.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException

private val modules = listOf(coreModule, remoteModule)

fun init(context: Context) {
    try {
        startKoin {
            androidContext(context)
            modules(modules)
        }
    } catch (alreadyStart: KoinAppAlreadyStartedException) {
        loadKoinModules(modules)
    }
}
