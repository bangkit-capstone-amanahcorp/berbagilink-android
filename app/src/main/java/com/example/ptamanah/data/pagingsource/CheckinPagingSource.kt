package com.example.ptamanah.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ptamanah.data.response.ResponseDataVisitorTenant
import com.example.ptamanah.data.response.DataItemtenant
import com.example.ptamanah.data.retrofit.ApiService

class CheckinPagingSource(
    private val apiService: ApiService,
    private val token: String,
    private val eventId: String,
    private val search: String?
) : PagingSource<Int, DataItemtenant>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, DataItemtenant>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItemtenant> {
        val page = params.key ?: INITIAL_PAGE_INDEX
        return try {
            val response = apiService.getChekin(
                token = "Bearer $token",
                search = search ?: "",
                limit = params.loadSize,
                page = page,
                sort = "desc",
                eventId = eventId
            )
            val data = response.datacheckin.data

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
