package com.example.ptamanah.viewModel.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ptamanah.data.repository.AuthRepo
import com.example.ptamanah.data.repository.EventRepository
import com.example.ptamanah.data.response.ResponseLogoutTenant
import com.example.ptamanah.data.response.ResponseTenantProfile
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class EventTenantViewModel(
    private val authRepo: AuthRepo
) : ViewModel() {

    suspend fun getTenantProfile(token: String): Flow<Result<ResponseTenantProfile>> {
        return authRepo.getTenantProfile(token)
    }
    fun getSessionTenant(): LiveData<String?>
    {
        return authRepo.getSessionTenant().asLiveData()
    }

    suspend fun logoutTenant(token: String): Flow<Result<ResponseLogoutTenant>> {
        return authRepo.logoutTenant(token)
    }

    fun logout() {
        viewModelScope.launch {
            authRepo.logout()
        }
    }
}