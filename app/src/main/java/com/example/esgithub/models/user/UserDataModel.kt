package com.example.esgithub.models.user

data class UserDataModel(
    val userId: String,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val bio: String?,
    val avatarUrl: String?
)
