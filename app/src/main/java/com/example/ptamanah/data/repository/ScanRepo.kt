package com.example.ptamanah.data.repository

import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.response.ResponseScan
import com.example.ptamanah.data.response.ResponseScanTenant
import com.example.ptamanah.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ScanRepo(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun scanEvent(auth: String, id: String, token: String): Flow<Result<ResponseScan>> =
        flow {
            val bearerToken = bearerToken(auth)
            val response = apiService.scanEvent(bearerToken, id, token)
            emit(Result.success(response))
        }.catch { e ->
            emit(Result.failure(e))
        }

    suspend fun scanTenant(auth: String, id: String, code: String): Flow<Result<ResponseScanTenant>> =
        flow {
            val bearerToken = bearerToken(auth)
            val response = apiService.scanTenant(bearerToken, id, code)
            emit(Result.success(response))
        }.catch { e ->
            emit(Result.failure(e))
        }

    fun getSessionTenant(): Flow<String?> = userPreference.getSessionTenant()

    private fun bearerToken(token: String): String {
        return "Bearer $token"
    }
}