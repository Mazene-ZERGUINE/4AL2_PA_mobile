package com.example.esgithub.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.esgithub.models.program.ProgramModel
import com.example.esgithub.models.program.request.LikeRequest
import com.example.esgithub.repositories.ProgramRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class ProgramViewModel(private val programRepository: ProgramRepository) : ViewModel() {
    private val disposeBag = CompositeDisposable()
    val programsData: MutableLiveData<List<ProgramModel>> = MutableLiveData<List<ProgramModel>>()

    val commentsCountsByProgram: MutableLiveData<Map<String, Int>> = MutableLiveData()

    init {
        getProgramData()
    }

    private fun getProgramData() {
        programRepository.getPublicPrograms().observeOn(Schedulers.io()).subscribe(
            {
                this.programsData.postValue(it)
                commentsCountsByProgram(it)
            },
            { e ->
                Log.d("program", e.message.toString())
            }
        ).addTo(disposeBag)
    }

    private fun commentsCountsByProgram(programList: List<ProgramModel>) {
        val initialCommentCounts = mutableMapOf<String, Int>()

        programList.forEach { program ->
            this.programRepository.getProgramComments(program.programId)
                .observeOn(Schedulers.io())
                .subscribe { comments ->
                    initialCommentCounts[program.programId] = comments.count { it.codeLineNumber == null }
                    commentsCountsByProgram.postValue(initialCommentCounts)
                    Log.d("commentsCount", initialCommentCounts[program.programId].toString())
                }.addTo(disposeBag)
        }
    }

    fun addLikeToAProgram(likeRequest: LikeRequest) {
        programRepository.addLike(likeRequest)
            .observeOn(Schedulers.io())
            .subscribe {
                this.getProgramData()
            }.addTo(disposeBag)
    }
}
