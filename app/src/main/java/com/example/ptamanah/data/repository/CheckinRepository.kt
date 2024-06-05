package com.example.ptamanah.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.ptamanah.data.pagingsource.CheckinChasierPagingSource
import com.example.ptamanah.data.pagingsource.CheckinPagingSource
import com.example.ptamanah.data.pagingsource.EventAdminLogPagingSource
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.response.DataItemAdmin
import com.example.ptamanah.data.response.DataItemCashier
import com.example.ptamanah.data.response.DataItemtenant
import com.example.ptamanah.data.response.ResponseDataVisitorTenant
import com.example.ptamanah.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class CheckinRepository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
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

    fun getCheckinLogAdmin(token: String, eventId: String, keywordValue: String?, status: String, isManual:Int?,startDate: String?, endDate: String?): LiveData<PagingData<DataItemAdmin>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { EventAdminLogPagingSource (apiService, token, eventId, keywordValue, status, isManual, startDate, endDate) }
        ).liveData
    }

    fun getUsername(): Flow<String?> = userPreference.getSessionAdmin()

}