package com.example.ptamanah.data.repository

import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.response.ResponseCheckEmail
import com.example.ptamanah.data.response.ResponseLogin
import com.example.ptamanah.data.response.ResponseLoginEventTenant
import com.example.ptamanah.data.response.ResponseLogoutTenant
import com.example.ptamanah.data.response.ResponseTenantProfile
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
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun saveAuthToken(token: String) {
        userPreference.saveSession(token)
    }

    suspend fun saveAuthAdmin(token: String) {
        userPreference.saveSessionAdmin(token)
    }

    suspend fun saveUsername(username: String) {
        userPreference.saveUsername(username)
    }

    fun getAuthTokenAdmin(): Flow<String?> = userPreference.getSessionAdmin()

    fun getAuthToken(): Flow<String?> = userPreference.getSession()


    suspend fun saveEmail(email: String) {
        userPreference.saveEmail(email)
    }

    fun getEmail(): Flow<String?> = userPreference.getEmail()

    suspend fun saveAuthTenant(token: String) {
        userPreference.saveSessionTenant(token)
    }

    suspend fun getTenantProfile(token: String): Flow<Result<ResponseTenantProfile>> = flow {
        try {
            val bearerToken = bearerToken(token)
            val response = apiService.tenantProfile(bearerToken)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    private fun bearerToken(token: String): String {
        return "Bearer $token"
    }

    fun getSessionTenant(): Flow<String?> = userPreference.getSessionTenant()

    suspend fun logout() {
        userPreference.logout()
    }

    suspend fun logoutTenant(token: String): Flow<Result<ResponseLogoutTenant>> = flow {
        try {
            val bearerToken = bearerToken(token)
            val response = apiService.logoutTenant(bearerToken)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}