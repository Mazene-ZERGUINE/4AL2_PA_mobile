package com.example.esgithub.models.reaction

import com.example.esgithub.models.user.UserDataModel

data class ReactionModel(
    val reactionId: String,
    val type: ReactionType?,
    val createdAt: String?,
    val user: UserDataModel
)
