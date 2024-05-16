package com.example.ptamanah.data.repository

import android.util.Log
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.response.ResponseCheckEmail
import com.example.ptamanah.data.response.ResponseLogin
import com.example.ptamanah.data.response.ResponseLoginEventTenant
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

    suspend fun checkEmail(email: String) : Flow<Result<ResponseCheckEmail>> = flow {
        try {
            val response = apiService.checkEmail(email)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun loginEvent(user: String, password: String, eventId: String) : Flow<Result<ResponseLoginEventTenant>> = flow {
        try {
            val response = apiService.loginEvent(user, password, eventId)
            Log.d("masukEvent", response.toString())
            emit(Result.success(response))
        } catch (e: Exception) {
            Log.d("gagalEvent", "oi")
            emit(Result.failure(e))
        }
    }

    suspend fun saveAuthToken(token: String) {
        userPreference.saveSession(token)
    }

    fun getAuthToken(): Flow<String?> = userPreference.getSession()


    suspend fun saveEmail(email: String) {
        userPreference.saveEmail(email)
    }

    fun getEmail(): Flow<String?> = userPreference.getEmail()

    suspend fun saveAuthTenant(token: String) {
        userPreference.saveSessionTenant(token)
    }

    fun getSessionTenant(): Flow<String?> = userPreference.getSessionTenant()

    suspend fun logout() {
        userPreference.logout()
    }

}