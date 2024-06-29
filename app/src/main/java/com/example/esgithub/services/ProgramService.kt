package com.example.esgithub.services

import com.example.esgithub.models.program.Comment
import com.example.esgithub.models.program.ProgramModel
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface ProgramService {
    @GET("programs?type=public")
    fun getPublicPrograms(): Flowable<List<ProgramModel>>

    @GET("comment/{programId}")
    fun getProgramComments(
        @Path("programId") programId: String
    ): Flowable<List<Comment>>
}
