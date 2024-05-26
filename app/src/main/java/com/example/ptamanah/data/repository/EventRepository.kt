package com.example.ptamanah.data.repository

import android.util.Log
import com.example.ptamanah.data.response.ResponseDataVisitorTenant
import com.example.ptamanah.data.response.ResponseListEvent
import com.example.ptamanah.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EventRepository(
    private val apiService: ApiService
) {


    suspend fun getEvent(token: String): Flow<Result<ResponseListEvent>> = flow {
        try {
            val bearerToken = bearerToken(token)
            val response = apiService.getEvents(bearerToken)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun getChekinrespon(token: String, eventId: String): Flow<Result<ResponseDataVisitorTenant>> = flow {
        try {
            val bearerToken = bearerToken(token)
            val response = apiService.getChekin(bearerToken, eventId = eventId)
            Log.d("ResponseIniBrok", response.toString())
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
            Log.e("Error", "Failed to fetch events", e)
        }
    }



    private fun bearerToken(token: String): String {
        return "Bearer $token"
    }
}