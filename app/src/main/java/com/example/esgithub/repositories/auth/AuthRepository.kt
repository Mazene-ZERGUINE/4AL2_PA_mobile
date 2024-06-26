package com.example.esgithub.repositories.auth

import com.example.esgithub.models.auth.LoginRequest
import com.example.esgithub.models.auth.LoginResponse
import com.example.esgithub.models.auth.SignupRequest
import com.example.esgithub.services.AuthService
import io.reactivex.rxjava3.core.Flowable

class AuthRepository(private val apiService: AuthService) {
    fun login(payload: LoginRequest): Flowable<LoginResponse> {
        return apiService.userLogin(payload)
    }

    fun signup(payload: SignupRequest): Flowable<Unit> {
        return this.apiService.signup(payload)
    }
}
