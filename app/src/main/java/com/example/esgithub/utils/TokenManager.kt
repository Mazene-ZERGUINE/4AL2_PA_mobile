package com.example.esgithub.utils

import android.content.Context
import android.util.Base64
import android.util.Log
import org.json.JSONObject

object TokenManager {
    private var pseudonymeFromToken = ""
    private lateinit var userIdFromToken: String
    private var userToken: String? = null

    fun setToken(token: String) {
        userToken = token
    }

    fun getToken(): String? {
        return userToken
    }

    fun clearToken() {
        userToken = null
    }

    private fun decodeToken(token: String?): JSONObject {
        if (token == null) {
            throw IllegalArgumentException("Token is null.")
        }

        val parts = token.split(".")

        if (parts.size != 3) {
            throw IllegalArgumentException("Invalid token.")
        }

        val payload = parts[1]
        val decodedBytes = Base64.decode(payload, Base64.URL_SAFE)
        val decodedPayload = String(decodedBytes)

        return JSONObject(decodedPayload)
    }

    fun getPseudonymeFromToken(): String {
        return pseudonymeFromToken
    }

    fun setPseudoAndUserIdFromToken() {
        val token = this.getToken() ?: return
        var jsonObject: JSONObject? = null

        try {
            jsonObject = this.decodeToken(token)
            pseudonymeFromToken = jsonObject.getString("username") ?: ""
            userIdFromToken = jsonObject.getString("sub") ?: ""
        } catch (e: Exception) {
            Log.d("decodeToken", "Error while decoding token.")
        }
    }

    fun getUserIdFromToken(): Int {
        return userIdFromToken.toInt()
    }

    fun storeAccessToken(
        context: Context,
        token: String
    ) {
        val sharedPreferences = context.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("ACCESS_TOKEN", token).apply()
    }
}
