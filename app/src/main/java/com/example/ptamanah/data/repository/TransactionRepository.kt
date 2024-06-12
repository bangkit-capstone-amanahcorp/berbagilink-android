package com.example.ptamanah.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.ptamanah.data.pagingsource.TransactionPagingSource
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.response.DataItemTransaction
import com.example.ptamanah.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class TransactionRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {
    fun getTransaction (token: String, eventId: String, search: String?, status: String?): Flow<PagingData<DataItemTransaction>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TransactionPagingSource(apiService, token, eventId, search, status) }
        ).flow
    }

    fun getUsername(): Flow<String?> = userPreference.getUsername()
}