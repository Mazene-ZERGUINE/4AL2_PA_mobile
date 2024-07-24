package com.example.esgithub.ui.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.esgithub.models.user.UserDataModel
import com.example.esgithub.repositories.UserRepository
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val disposeBad = CompositeDisposable()

    val currentUserData = MutableLiveData<UserDataModel>()

    init {
        this.getCurrentUserData()
    }

    private fun getCurrentUserData() {
        userRepository.getCurrentUserInfo().observeOn(Schedulers.io()).subscribe(
            {
                this.currentUserData.postValue(it)
                Log.d("currentUserInfo", it.toString())
            },
            {
                Log.d("currentUserInfo", it.message.toString())
            }
        ).addTo(disposeBad)
    }
}
