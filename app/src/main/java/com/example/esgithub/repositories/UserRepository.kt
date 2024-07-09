package com.example.esgithub.repositories

import com.example.esgithub.models.user.UserDataModel
import com.example.esgithub.services.UserService
import io.reactivex.rxjava3.core.Flowable

class UserRepository(private val userService: UserService) {
    fun getCurrentUserInfo(): Flowable<UserDataModel> {
        return userService.getCurrentUserInfo()
    }
}
