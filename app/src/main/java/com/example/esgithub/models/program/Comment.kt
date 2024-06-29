package com.example.esgithub.models.program

import com.example.esgithub.models.user.UserDataModel

data class Comment(
    val commentId: String,
    val content: String,
    val replies: List<Comment>,
    val user: UserDataModel,
    val createdAt: String,
    val updatedAt: String,
    val codeLineNumber: Int?
)
