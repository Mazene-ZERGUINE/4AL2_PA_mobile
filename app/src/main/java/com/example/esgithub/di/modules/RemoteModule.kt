package com.example.esgithub.di.modules

import android.content.Context
import com.example.esgithub.di.JsonConf
import com.example.esgithub.services.AuthService
import com.example.esgithub.utils.TokenManager
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val AUTH_SERVICE = "authService"

internal val remoteModule =
    module {

        single(named(AUTH_SERVICE)) { createRetrofitClient(get(), get<JsonConf>().baseUrl) }

        single {
            createWebService<AuthService>(
                get(named(AUTH_SERVICE))
            )
        }

        single { createOkHttpClient(androidContext()) }
    }

fun createAuthorizationInterceptor(context: Context): Interceptor {
    return Interceptor { chain ->
        val token = TokenManager.getToken()

        val requestOriginal = chain.request()
        val requestModifie =
            if (token != null) {
                requestOriginal.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
            } else {
                requestOriginal
            }

        chain.proceed(requestModifie)
    }
}

fun createOkHttpClient(context: Context): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(createAuthorizationInterceptor(context))
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()
}

inline fun <reified T> createWebService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}

fun createRetrofitClient(
    client: OkHttpClient,
    apiUrl: String
): Retrofit {
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
