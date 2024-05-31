package com.example.ptamanah.viewModel.checkin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ptamanah.data.repository.CheckinRepository
import com.example.ptamanah.data.response.DataItemCashier
import com.example.ptamanah.data.response.DataItemtenant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class LogcheckinCashierViewModel(private val checkinRepository: CheckinRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow<String?>(null)

    fun getCheckinscashier(token: String, eventId: String): LiveData<PagingData<DataItemCashier>> {
        Log.d("masokVM", "Token received: $token")
        Log.d("masokVM", "EventId received: $eventId")

        return _searchQuery.flatMapLatest { query ->
            Log.d("masokVM", "Searching for query: $query with token: $token and eventId: $eventId")
            checkinRepository.getCheckinChasier(token, eventId, query)
        }.cachedIn(viewModelScope).asLiveData()
    }

    fun searchUsercashier(query: String?) {
        Log.d("searchCashierViewModel", "Search query updated: $query")
        _searchQuery.value = query
    }
}
