package com.example.esgithub.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

object SecureStorage {
    private const val PREFERENCES_FILE_NAME = "secure_prefs"
    private const val KEY = "token"

    private fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
        val masterKey =
            MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

        return EncryptedSharedPreferences.create(
            context,
            PREFERENCES_FILE_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveToken(
        context: Context,
        token: String
    ) {
        val encryptedPrefs = getEncryptedSharedPreferences(context)
        encryptedPrefs.edit().putString(KEY, token).apply()
    }

    fun getToken(context: Context): String? {
        val encryptedPrefs = getEncryptedSharedPreferences(context)
        return encryptedPrefs.getString(KEY, null)
    }

    fun clearToken(context: Context) {
        val encryptedPrefs = getEncryptedSharedPreferences(context)
        encryptedPrefs.edit().remove(KEY).apply()
    }
}
