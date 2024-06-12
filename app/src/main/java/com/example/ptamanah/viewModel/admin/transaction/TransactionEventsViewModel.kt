package com.example.ptamanah.viewModel.admin.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ptamanah.data.repository.TransactionRepository
import com.example.ptamanah.data.response.DataItemTransaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class TransactionEventsViewModel(private val transactionRepository: TransactionRepository) : ViewModel() {
    private val _searchQuery = MutableStateFlow<Pair<String?, String?>>(null to null)

    fun getTransaksi(token: String, eventId: String): Flow<PagingData<DataItemTransaction>> {
        return _searchQuery.flatMapLatest { (query, status) ->
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
