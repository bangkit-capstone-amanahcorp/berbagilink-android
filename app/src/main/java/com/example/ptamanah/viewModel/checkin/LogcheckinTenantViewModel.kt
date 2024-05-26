package com.example.ptamanah.viewModel.checkin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ptamanah.data.repository.EventRepository
import com.example.ptamanah.data.response.DataItemtenant
import com.example.ptamanah.data.response.ResponseDataVisitorTenant
import kotlinx.coroutines.launch

class LogcheckinTenantViewModel (private val eventRepository: EventRepository) : ViewModel() {
        private val _checkinData = MutableLiveData<Result<ResponseDataVisitorTenant>>()
        val checkinData: LiveData<Result<ResponseDataVisitorTenant>> = _checkinData

        private val _filteredCheckinData = MutableLiveData<List<DataItemtenant>>()
        val filteredCheckinData: LiveData<List<DataItemtenant>> = _filteredCheckinData

    fun getAllCheckins(token: String, eventId: String) {
        viewModelScope.launch {
            eventRepository.getChekinrespon(token, eventId).collect { result ->
                _checkinData.value = result
                _filteredCheckinData.value = result.getOrNull()?.datacheckin?.data ?: emptyList()
            }
        }
    }


    //searchlogic
    fun searchUser(query: String) {
        val searchResult = _checkinData.value?.getOrNull()?.datacheckin?.data?.filter {
            it.nama.contains(query, ignoreCase = true)
        } ?: emptyList()
        _filteredCheckinData.value = searchResult
    }
}