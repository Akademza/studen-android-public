package com.example.studen_android.data.repositories

import android.util.Log
import android.webkit.CookieManager
import androidx.core.content.edit
import com.example.studen_android.BuildConfig
import com.example.studen_android.data.PreferenceStorage
import com.example.studen_android.data.models.HttpError
import com.example.studen_android.data.models.User
import com.example.studen_android.data.networking.RetrofitClient.endpointsInterface
import com.example.studen_android.helpers.HttpErrors
import com.example.studen_android.helpers.format
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*

object MainRepository {

    private const val TOKEN = "token"

    suspend fun auth(uuid: String, playerID: String, isModal: Boolean): Result<User> {
        val time = Calendar.getInstance().getTime().format()
        val isModal = if (isModal) "chat" else ""
        val response = endpointsInterface.auth(
            key = BuildConfig.API_KEY,
            uuid = uuid,
            include_player_id = playerID,
            url = "",
            modal = isModal,
            time = time
        )
        val body = response.body()
        val authorization = response.headers().first { it.first == "authorization" }.second

        return if (response.isSuccessful && response.code() == 201 && body != null) {
            saveAuthToken(authorization)
            Result.success(body)
        } else if (response.code() == 401) {
            Result.failure(HttpErrors.Unauthorized)
        } else if (response.code() == 400) {
            val text = Json {
                ignoreUnknownKeys = true
            }.decodeFromString<HttpError>(response.errorBody()!!.string())
            Result.failure(HttpErrors.LocalizedError(text.message))
        } else {
            Result.failure(HttpErrors.UnknownError)
        }
    }

    fun saveAuthToken(token: String) {
        PreferenceStorage.authPrefs.edit {
            putString(TOKEN, token)
        }
    }

    fun getAuthToken(): String? {
        return PreferenceStorage.authPrefs.getString(TOKEN, "")
    }

    fun clearToken() {
        PreferenceStorage.authPrefs.edit { clear() }
    }
}