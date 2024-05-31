package com.example.ptamanah.viewModel.checkin

import androidx.lifecycle.*
import androidx.paging.*
import com.example.ptamanah.data.repository.CheckinRepository
import com.example.ptamanah.data.response.DataItemtenant
import kotlinx.coroutines.flow.*

class LogcheckinTenantViewModel(private val checkinRepository: CheckinRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow<String?>(null)

    fun getCheckins(token: String, eventId: String): LiveData<PagingData<DataItemtenant>> {
        return _searchQuery.flatMapLatest { query ->
            checkinRepository.getCheckinStream(token, eventId, query)
        }.cachedIn(viewModelScope).asLiveData()
    }

    fun searchUser(query: String?) {
        _searchQuery.value = query
    }
}
