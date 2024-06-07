package com.example.ptamanah.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.response.DataItemManagementUser
import com.example.ptamanah.data.response.ResponseManagementUser
import com.example.ptamanah.data.response.ResponseManagementUserAdd
import com.example.ptamanah.data.response.Responsedelete
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.data.retrofit.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ManagementUserRepository(
    private val apiService : ApiService
) {
    suspend fun getManagementUser(token: String): Flow<Result<ResponseManagementUser>> = flow {
        try {
            val bearerToken = bearerToken(token)
            val response = apiService.getManageUser(bearerToken)
            Log.d("afaahasilnya", response.toString())
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
            Log.e("Error", "Failed to fetch events", e)
        }
    }

    suspend fun deleteUser(token: String, id: Int): Flow<Result<Responsedelete>> = flow{
        try {
            val bearerToken = bearerToken(token)
            val response = apiService.deleteUser(bearerToken, id)
            Log.d("afaahasilnya", response.toString())
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
            Log.e("Error", "Failed to fetch events", e)
        }
    }

    suspend fun addUser(token: String, name: String, password: String,passwordConfirmation: String, email: String,role: String): Flow<Result<ResponseManagementUserAdd>> = flow {
        try {
            val bearerToken = bearerToken(token)
            val response = apiService.addUser(bearerToken, name, password,passwordConfirmation,email,role)
            Log.d("afaahasilnya", response.toString())
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
            Log.e("Error", "Failed to fetch add", e)
        }
    }
}


private fun bearerToken(token: String): String {
    return "Bearer $token"
}