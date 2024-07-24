package com.example.esgithub.models.reaction

import com.google.gson.annotations.SerializedName

enum class ReactionType(val type: String) {
    @SerializedName("like")
    LIKE("like"),

    @SerializedName("dislike")
    DISLIKE("dislike")
}
