package com.example.esgithub.services

import com.example.esgithub.models.program.Comment
import com.example.esgithub.models.program.ProgramModel
import com.example.esgithub.models.program.request.CommentRequest
import com.example.esgithub.models.program.request.LikeRequest
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProgramService {
    @GET("programs?type=public")
    fun getPublicPrograms(): Flowable<List<ProgramModel>>

    @GET("comment/{programId}")
    fun getProgramComments(
        @Path("programId") programId: String
    ): Flowable<List<Comment>>

    @POST("comment")
    fun addComment(
        @Body commentRequest: CommentRequest
    ): Flowable<Unit>

    @POST("reactions/like")
    fun addLike(
        @Body likeRequest: LikeRequest
    ): Flowable<Unit>
}
