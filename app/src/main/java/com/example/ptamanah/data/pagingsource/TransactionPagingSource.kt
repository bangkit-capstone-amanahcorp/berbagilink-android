package com.example.ptamanah.data.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.ptamanah.data.response.DataItemCashier
import com.example.ptamanah.data.response.DataItemTransaction
import com.example.ptamanah.data.retrofit.ApiService

class TransactionPagingSource(
    private val apiService: ApiService,
    private val token: String,
    private val eventId: String,
    private val search: String?,
    private val status: String?
): PagingSource<Int, DataItemTransaction>() {
    init {
        Log.d("TransaksiPagingSource", "Initialized with eventId: $eventId")
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, DataItemTransaction>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItemTransaction> {
        val page = params.key ?: INITIAL_PAGE_INDEX
        return try {
            Log.d("TransaksiPagingSource", "Loading page $page for eventId: $eventId")

            val response = apiService.getTransaction(
                token = "Bearer $token",
                eventId = eventId,
                page = page,
                search = search ?: "",
                status = status ?: ""
            )
            val data = response.dataTransaction.data

            Log.d("TransaksiPagingSource", "Loaded page $page with ${data.size} items for eventId: $eventId")

            LoadResult.Page(
                data = data,
                prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1,
                nextKey = if (data.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            Log.e("Logepagingtransaksi", "Error loading data for eventId: $eventId", exception)
            LoadResult.Error(exception)
        }
    }
}
