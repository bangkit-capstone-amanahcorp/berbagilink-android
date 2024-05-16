package com.example.ptamanah.viewModel.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ptamanah.data.repository.AuthRepo
import com.example.ptamanah.data.repository.EventRepository
import kotlinx.coroutines.launch

class EventTenantViewModel(
    private val authRepo: AuthRepo
) : ViewModel() {


    fun getSessionTenant(): LiveData<String?>
    {
        return authRepo.getSessionTenant().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            authRepo.logout()
        }
    }
}