package com.example.esgithub.services

import com.example.esgithub.models.user.UserDataModel
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.GET

interface UserService {
    @GET("auth/get_info")
    fun getCurrentUserInfo(): Flowable<UserDataModel>
}
