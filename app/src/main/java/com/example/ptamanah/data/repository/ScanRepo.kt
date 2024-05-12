package com.example.ptamanah.data.repository

import android.util.Log
import com.example.ptamanah.data.response.ResponseScan
import com.example.ptamanah.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class ScanRepo(
    private val apiService: ApiService,
    ) {

    suspend fun scanEvent(auth: String, id: String, token: String): Flow<Result<ResponseScan>> = flow {
        Log.d("usermasuk22", "msk")
        val bearerToken = bearerToken(auth)
        val response = apiService.scanEvent(bearerToken, id, token)
        Log.d("userLogincath122", response.toString())
        emit(Result.success(response))
    }.catch { e ->
        emit(Result.failure(e))
        Log.d("userLogincath232", e.toString())
    }


    private fun bearerToken(token: String): String {
        return "Bearer $token"
    }
}