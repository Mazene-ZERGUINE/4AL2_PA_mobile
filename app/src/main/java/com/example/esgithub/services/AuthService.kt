package com.example.esgithub.services

import com.example.esgithub.models.auth.LoginRequest
import com.example.esgithub.models.auth.LoginResponse
import com.example.esgithub.models.auth.SignupRequest
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    fun userLogin(
        @Body request: LoginRequest
    ): Flowable<LoginResponse>

    @POST("auth/sign-up")
    fun signup(
        @Body request: SignupRequest
    ): Flowable<Unit>
}
