package com.example.esgithub.models.program.request

data class CommentRequest(
    private val content: String,
    private val userId: String,
    val programId: String
)
