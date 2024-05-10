package com.example.ptamanah.data.repository

import android.util.Log
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.response.ResponseLogin
import com.example.ptamanah.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepo(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun userLogin(email: String, password: String): Flow<Result<ResponseLogin>> = flow {
        try {
            val response = apiService.userLogin(email, password)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun saveAuthToken(token: String) {
        userPreference.saveSession(token)
    }

    fun getAuthToken(): Flow<String?> = userPreference.getSession()

    suspend fun logout() {
        userPreference.logout()
    }

}