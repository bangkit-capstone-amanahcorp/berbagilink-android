package com.example.ptamanah.data.repository

import com.example.ptamanah.data.response.ResponseScan
import com.example.ptamanah.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ScanRepo(
    private val apiService: ApiService,
) {

    suspend fun scanEvent(auth: String, id: String, token: String): Flow<Result<ResponseScan>> =
        flow {
            val bearerToken = bearerToken(auth)
            val response = apiService.scanEvent(bearerToken, id, token)
            emit(Result.success(response))
        }.catch { e ->
            emit(Result.failure(e))
        }

    private fun bearerToken(token: String): String {
        return "Bearer $token"
    }
}