package com.example.esgithub.ui.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.esgithub.models.auth.LoginRequest
import com.example.esgithub.models.auth.LoginResponse
import com.example.esgithub.repositories.AuthRepository
import com.example.esgithub.utils.TokenManager
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val disposBag = CompositeDisposable()

    private val _loginResult = MutableLiveData<LoginResponse>()
    val loginResult: LiveData<LoginResponse> = _loginResult

    private val _loginError = MutableLiveData<String>()
    val loginError: LiveData<String> = _loginError

    fun login(
        email: String,
        password: String
    ) {
        val payload = LoginRequest(email, password)

        Log.d("palyload", payload.toString())

        authRepository.login(payload)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { response ->

                    _loginResult.postValue(response)
                    TokenManager.getPseudonymeFromToken()
                },
                { error ->
                    _loginError.postValue(error.message)
                    Log.d("auth response", error.message.toString())
                }
            ).addTo(disposBag)
    }
}
