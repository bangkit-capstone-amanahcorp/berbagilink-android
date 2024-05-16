package com.example.ptamanah.viewModel.checkin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ptamanah.data.repository.EventRepository
import com.example.ptamanah.data.response.ResponseDataVisitorTenant
import kotlinx.coroutines.launch

class LogcheckinTenantViewModel (private val eventRepository: EventRepository) : ViewModel() {
        private val _checkinData = MutableLiveData<Result<ResponseDataVisitorTenant>>()
        val checkinData: LiveData<Result<ResponseDataVisitorTenant>> = _checkinData

        fun getAllCheckins(token: String) {
            viewModelScope.launch {
                eventRepository.getChekinrespon(token).collect { result ->
                    _checkinData.value = result
                }
            }
        }

        fun searchuser(query : String){
            viewModelScope.launch { {

            } }
        }

    }