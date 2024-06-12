package com.example.ptamanah.viewModel.checkin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.ptamanah.data.repository.CheckinRepository
import com.example.ptamanah.data.response.DataItemAdmin
import kotlinx.coroutines.flow.MutableStateFlow

class EventAdminViewModel (private val checkinRepository: CheckinRepository) : ViewModel() {

    private val _searchQuery = MutableStateFlow<String?>(null)

    fun getUsername() : LiveData<String?> {
        return checkinRepository.getUsername().asLiveData()
    }

    fun getCheckinLogAdmin(token: String, eventId: String, keywordValue: String?, status: String, isManual: Int?, startDate: String?, endDate: String?): LiveData<PagingData<DataItemAdmin>> {
        return checkinRepository.getCheckinLogAdmin(token, eventId, keywordValue, status, isManual, startDate, endDate).cachedIn(viewModelScope)
    }

    fun searchUser(query: String?) {
        _searchQuery.value = query
    }
}
