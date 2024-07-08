package com.example.esgithub.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.esgithub.models.program.Comment
import com.example.esgithub.models.program.request.CommentRequest
import com.example.esgithub.repositories.ProgramRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class CommentViewModel(private val programRepository: ProgramRepository) : ViewModel() {
    private val disposeBag = CompositeDisposable()

    val programComments = MutableLiveData<List<Comment>>()

    fun loadProgramComments(programId: String) {
        this.getAllComments(programId)
    }

    private fun getAllComments(programId: String) {
        this.programRepository.getProgramComments(programId).observeOn(Schedulers.io()).subscribe(
            { programComments ->

                val commentsBelowProgram = programComments.filter { it.codeLineNumber == null }

                this.programComments.postValue(commentsBelowProgram)

                Log.d("comments", programComments.toString())
            },
            {
                Log.d("gettingComments", it.message.toString())
            }
        ).addTo(disposeBag)
    }

    fun addComment(commentRequest: CommentRequest)  {
        this.programRepository.addComment(commentRequest).observeOn(Schedulers.io()).subscribe {
            loadProgramComments(commentRequest.programId)
        }.addTo(disposeBag)
    }
}
