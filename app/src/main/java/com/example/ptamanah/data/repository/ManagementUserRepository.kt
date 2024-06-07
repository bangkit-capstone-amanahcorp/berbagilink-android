package com.example.ptamanah.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.response.DataItemManagementUser
import com.example.ptamanah.data.response.DataUpdate
import com.example.ptamanah.data.response.ResponseChangePassword
import com.example.ptamanah.data.response.ResponseManagementUser
import com.example.ptamanah.data.response.ResponseUpdateUser
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
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
            Log.e("Error", "Failed to fetch events", e)
        }
    }

    suspend fun updateUser(token: String, id: Int, nama: String, email:String, role: String): Flow<Result<ResponseUpdateUser>> = flow {
        try {
            val bearerToken = bearerToken(token)
            val response = apiService.updateUser(bearerToken, id, nama, email, role)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun changePassword(token: String, id: Int, password: String, confirmPassword: String): Flow<Result<ResponseChangePassword>> = flow {
        try {
            val bearerToken = bearerToken(token)
            Log.d("afaahasilnya22", id.toString())
            val response = apiService.changePassword(bearerToken, id, password, confirmPassword)
            Log.d("afaahasilnya223", response.toString())
            emit(Result.success(response))
        } catch (e: Exception) {
            Log.d("afaahasilnya224", e.toString())
            emit(Result.failure(e))
        }
    }
}


private fun bearerToken(token: String): String {
    return "Bearer $token"
}