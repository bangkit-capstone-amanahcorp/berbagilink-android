package com.example.ptamanah.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ptamanah.data.response.DataItemLog
import com.example.ptamanah.data.response.DataItemtenant
import com.example.ptamanah.data.retrofit.ApiService

class EventAdminLogPagingSource(
    private val apiService: ApiService,
    private val token: String,
    private val eventId: String,
    private val search: String?,
    private val status: String
) : PagingSource<Int, DataItemLog>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, DataItemLog>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItemLog> {
        val page = params.key ?: INITIAL_PAGE_INDEX
        return try {
            val response = apiService.getEventAdmin(
                token = "Bearer $token",
                search = search ?: "",
                page = page,
                eventId = eventId,
                status = status

            )
            val data = response.dataCheckinLogAdmin.dataAd.data

            LoadResult.Page(
                data = data,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}