package com.example.es_github.di.modules

import android.content.Context
import com.example.es_github.utils.AuthInterceptor
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_URL = "192.168.0.2"
const val API_SERVICE = "apiService"


internal val remoteModule = module {
    single(named(API_SERVICE)) { createRetrofitClient(get(), API_URL)}
    single { createOkHttpClient(androidContext()) }
}

fun createOkHttpClient(context: Context): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor(context))
        .build()
}

fun createRetrofitClient(client: OkHttpClient, apiUrl: String): Retrofit {
    val gsonConverter =
        GsonConverterFactory.create(
            GsonBuilder().create()
        )

    return Retrofit.Builder()
        .baseUrl(apiUrl)
        .addConverterFactory(gsonConverter)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(client)
        .build()
}
// add interceptors configurations here