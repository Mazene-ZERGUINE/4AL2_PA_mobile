package com.example.esgithub.ui.view.adapters.interfaces

import com.example.esgithub.models.program.request.LikeRequest

interface OnProgramLiked {
    fun addLikeToAProgram(likeRequest: LikeRequest)
}
