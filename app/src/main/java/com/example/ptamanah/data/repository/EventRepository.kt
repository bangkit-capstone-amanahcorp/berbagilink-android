package com.example.ptamanah.data.repository

import android.util.Log
import com.example.ptamanah.data.response.DataItem
import com.example.ptamanah.data.response.ResponseDataVisitorTenant
import com.example.ptamanah.data.response.ResponseDetailEvents
import com.example.ptamanah.data.response.ResponseListEvent
import com.example.ptamanah.data.response.ResponseReportStatistic
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

    suspend fun eventDetail(token: String, id: String): Flow<Result<ResponseDetailEvents>> = flow {
        try {
            val bearerToken = bearerToken(token)
            Log.d("response", bearerToken.toString())
            val response = apiService.eventDetail(bearerToken, id)
            Log.d("response1", response.toString())
            emit(Result.success(response))
            Log.d("response2", response.toString())
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun getReportStatistic(token: String, id: String): Flow<Result<ResponseReportStatistic>> = flow {
        try {
            val bearerToken = bearerToken(token)
            val response = apiService.getReportStatistic(bearerToken, id)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    private fun bearerToken(token: String): String {
        return "Bearer $token"
    }
}