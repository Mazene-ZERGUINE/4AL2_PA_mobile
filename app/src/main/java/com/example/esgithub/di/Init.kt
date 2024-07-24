package com.example.esgithub.di

import android.content.Context
import com.example.esgithub.di.modules.coreModule
import com.example.esgithub.di.modules.remoteModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException
import org.koin.dsl.module

const val API_URL = "http://10.0.2.2:3000/api/v1/"

fun injectModuleDependencies(context: Context) {
    try {
        startKoin {
            androidContext(context)
            modules(modules)
        }
    } catch (alreadyStart: KoinAppAlreadyStartedException) {
        loadKoinModules(modules)
    }
}

fun parseAndInjectConfiguration() {
    val apiConf = JsonConf(baseUrl = API_URL)
    modules.add(
        module {
            single { apiConf }
        }
    )
}

private val modules = mutableListOf(coreModule, remoteModule)

data class JsonConf(val baseUrl: String)
