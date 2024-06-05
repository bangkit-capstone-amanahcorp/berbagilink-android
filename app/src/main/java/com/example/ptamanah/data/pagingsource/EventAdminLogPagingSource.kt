package com.example.ptamanah.data.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ptamanah.data.response.DataItemAdmin
import com.example.ptamanah.data.retrofit.ApiService

class EventAdminLogPagingSource(
    private val apiService: ApiService,
    private val token: String,
    private val eventId: String,
    private val keywordValue: String?,
    private val status: String,
    private val isManual: Int?,
    private val startDate: String?,
    private val endDate: String?,
) : PagingSource<Int, DataItemAdmin>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, DataItemAdmin>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItemAdmin> {
        val page = params.key ?: INITIAL_PAGE_INDEX
        return try {
            val response = apiService.getEventAdmin(
                token = "Bearer $token",
                eventId = eventId,
                page = page,
                startDate = startDate?:"",
                endDate = endDate?:"",
                keywordValue = keywordValue ?: "",
                status = status,
                isManual = isManual,
            )
            Log.d("Response22", response.toString())
            val data = response.data?.data?.filterNotNull() ?: emptyList()

            Log.d("Data22", data.toString())
            LoadResult.Page(
                data = data,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            Log.e("API Error", exception.toString())
            LoadResult.Error(exception)
        }
    }
}