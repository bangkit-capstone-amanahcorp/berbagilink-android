package com.example.ptamanah.viewModel.checkin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ptamanah.data.repository.CheckinRepository
import com.example.ptamanah.data.response.DataItemLog
import com.example.ptamanah.data.response.DataItemtenant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class EventAdminViewModel (private val checkinRepository: CheckinRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow<String?>(null)

    fun getCheckinLogAdmin(token: String, eventId: String, search: String?, status: String): Flow<PagingData<DataItemLog>> {
        return checkinRepository.getCheckinLogAdmin(token, eventId, search, status)
            .cachedIn(viewModelScope)
    }

    fun searchUser(query: String?) {
        _searchQuery.value = query
    }
}
