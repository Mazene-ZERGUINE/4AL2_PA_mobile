package com.example.esgithub.models.auth

data class LoginRequest(private val email: String, private val password: String)

data class LoginResponse(private val accessToken: String) {
    fun getToken(): String {
        return accessToken
    }
}

data class SignupRequest(
    private val firstName: String,
    private val lastName: String,
    private val email: String,
    private val password: String,
    private val userName: String
)

data class SignupResponse(private val success: Boolean)
