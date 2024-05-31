package com.example.ptamanah.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.ptamanah.data.pagingsource.CheckinChasierPagingSource
import com.example.ptamanah.data.pagingsource.CheckinPagingSource
import com.example.ptamanah.data.response.DataItemCashier
import com.example.ptamanah.data.response.DataItemtenant
import com.example.ptamanah.data.response.ResponseDataVisitorTenant
import com.example.ptamanah.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class CheckinRepository(
    private val apiService: ApiService
) {
    fun getCheckinStream(token: String, eventId: String, search: String?): Flow<PagingData<DataItemtenant>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CheckinPagingSource(apiService, token, eventId, search) }
        ).flow
    }

    fun getCheckinChasier(token: String, eventId: String, search: String?): Flow<PagingData<DataItemCashier>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CheckinChasierPagingSource(apiService, token, eventId, search) }
        ).flow
    }
}