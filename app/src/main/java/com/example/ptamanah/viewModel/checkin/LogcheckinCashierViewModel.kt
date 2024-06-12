package com.example.ptamanah.viewModel.checkin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ptamanah.data.repository.CheckinRepository
import com.example.ptamanah.data.response.DataItemCashier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class LogcheckinCashierViewModel(private val checkinRepository: CheckinRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow<String?>(null)

    fun getCheckinscashier(token: String, eventId: String): Flow<PagingData<DataItemCashier>> {

        return _searchQuery.flatMapLatest { query ->
            checkinRepository.getCheckinChasier(token, eventId, query)
        }.cachedIn(viewModelScope)
    }

    fun searchUsercashier(query: String?) {
        _searchQuery.value = query
    }
}
