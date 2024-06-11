package com.example.ptamanah.viewModel.admin.transaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ptamanah.data.repository.CheckinRepository
import com.example.ptamanah.data.repository.TransactionRepository
import com.example.ptamanah.data.response.DataItemCashier
import com.example.ptamanah.data.response.DataItemTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class TransactionEventsViewModel(private val transactionRepository: TransactionRepository) : ViewModel() {
    private val _searchQuery = MutableStateFlow<Pair<String?, String?>>(null to null)

    fun getTransaksi(token: String, eventId: String): Flow<PagingData<DataItemTransaction>> {
        Log.d("TransactionEventsViewModel", "Token received: $token")
        Log.d("TransactionEventsViewModel", "EventId received: $eventId")

        return _searchQuery.flatMapLatest { (query, status) ->
            Log.d("TransactionEventsViewModel", "Searching for query: $query with status: $status")
            transactionRepository.getTransaction(token, eventId, query, status)
        }.cachedIn(viewModelScope)
    }

    fun searchTransaksi(query: String?) {
        _searchQuery.value = query to _searchQuery.value.second
    }

    fun filterTransaksi(status: String?) {
        _searchQuery.value = _searchQuery.value.first to status
    }
}
