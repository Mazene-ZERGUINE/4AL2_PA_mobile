package com.example.esgithub.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.esgithub.models.auth.SignupRequest
import com.example.esgithub.repositories.auth.AuthRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class SignupViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val disposeBag = CompositeDisposable()
    val signupResult = MutableLiveData<Boolean>()
    val signupErr = MutableLiveData<Throwable>()

    fun signup(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        userName: String
    ) {
        val payload = SignupRequest(firstName, lastName, email, password, userName)
        this.authRepository.signup(payload)
            .observeOn(Schedulers.io())
            .subscribeBy(
                onNext = {
                    Log.d("signup", "login success")
                    signupResult.postValue(true)
                },
                onError = { error ->
                    Log.d("signup error", error.message.toString())
                    signupErr.postValue(error)
                }
            )
            .addTo(disposeBag)
    }

    override fun onCleared() {
        super.onCleared()
        disposeBag.clear()
    }
}
