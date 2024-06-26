package com.example.esgithub.models.program

import com.example.esgithub.models.FileTypesEnum
import com.example.esgithub.models.reaction.ReactionModel
import com.example.esgithub.models.user.UserDataModel

data class ProgramModel(
    val programId: String,
    val description: String?,
    val programmingLanguage: String,
    val sourceCode: String,
    val visibility: String,
    val inputTypes: List<FileTypesEnum>,
    val outputTypes: List<FileTypesEnum>,
    val user: UserDataModel,
    val userId: String,
    val reactions: List<ReactionModel>,
    val createdAt: String,
    val updatedAt: String
)
