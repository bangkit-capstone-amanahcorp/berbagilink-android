package com.example.ptamanah.data.repository

import com.example.ptamanah.data.response.voucher.ResponseListVoucher
import com.example.ptamanah.data.response.voucher.ResponseVoucher
import com.example.ptamanah.data.retrofit.ApiService

class VoucherRepository(private val apiService: ApiService) {

    suspend fun getVoucher(token: String): Result<ResponseListVoucher> {
        return try {
            val bearerToken = bearerToken(token)
            val response = apiService.getVoucher(bearerToken)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addVoucher(
        token: String,
        name: String,
        code: String,
        type: String,
        feature: String,
        usage: String,
        value: Float,
        minValue: Int,
        maxValue: Int,
        quota: Int,
        used: Int,
        description: String,
        isActive: Boolean
    ): Result<ResponseVoucher> {
        return try {
            val response = apiService.addVoucher(
                token,
                name,
                code,
                type,
                feature,
                usage,
                value,
                minValue,
                maxValue,
                quota,
                used,
                description,
                isActive
            )

            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    private fun bearerToken(token: String): String {
        return "Bearer $token"
    }
}