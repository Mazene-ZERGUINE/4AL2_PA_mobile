package com.example.esgithub.repositories

import com.example.esgithub.models.program.Comment
import com.example.esgithub.models.program.ProgramModel
import com.example.esgithub.services.ProgramService
import io.reactivex.rxjava3.core.Flowable

class ProgramRepository(private val programService: ProgramService) {
    fun getPublicPrograms(): Flowable<List<ProgramModel>>  {
        return programService.getPublicPrograms()
    }

    fun getProgramComments(programId: String): Flowable<List<Comment>>  {
        return programService.getProgramComments(programId)
    }
}
