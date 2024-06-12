package com.example.ptamanah.data.repository

import com.example.ptamanah.data.response.ResponseChangePassword
import com.example.ptamanah.data.response.ResponseManagementUser
import com.example.ptamanah.data.response.ResponseManagementUserAdd
import com.example.ptamanah.data.response.ResponseUpdateUser
import com.example.ptamanah.data.response.Responsedelete
import com.example.ptamanah.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
class ManagementUserRepository(
    private val apiService : ApiService
) {
    suspend fun getManagementUser(token: String): Flow<Result<ResponseManagementUser>> = flow {
        try {
            val bearerToken = bearerToken(token)
            val response = apiService.getManageUser(bearerToken)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun deleteUser(token: String, id: Int): Flow<Result<Responsedelete>> = flow {
        try {
            val bearerToken = bearerToken(token)
            val response = apiService.deleteUser(bearerToken, id)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    suspend fun addUser(
        token: String,
        name: String,
        password: String,
        confirmPassword: String,
        email: String,
        role: String
    ): Flow<Result<ResponseManagementUserAdd>> = flow {
        try {
            val bearerToken = bearerToken(token)
            val response =
                apiService.addUser(bearerToken, name, password, confirmPassword, email, role)
            emit(Result.success(response))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

        suspend fun updateUser(
            token: String,
            id: Int,
            nama: String,
            email: String,
            role: String
        ): Flow<Result<ResponseUpdateUser>> = flow {
            try {
                val bearerToken = bearerToken(token)
                val response = apiService.updateUser(bearerToken, id, nama, email, role)
                emit(Result.success(response))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }

        suspend fun changePassword(
            token: String,
            id: Int,
            password: String,
            confirmPassword: String
        ): Flow<Result<ResponseChangePassword>> = flow {
            try {
                val bearerToken = bearerToken(token)
                val response = apiService.changePassword(bearerToken, id, password, confirmPassword)
                emit(Result.success(response))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
    }


    private fun bearerToken(token: String): String {
        return "Bearer $token"
    }