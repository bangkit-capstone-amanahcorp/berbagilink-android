package com.example.ptamanah.viewModel.mainadmin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ptamanah.data.repository.AuthRepo
import kotlinx.coroutines.launch

class HomePageAdminViewModel (private val authRepo: AuthRepo) : ViewModel() {
    fun getSession() : LiveData<String?> {
        return authRepo.getAuthTokenAdmin().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            authRepo.logout()
        }
    }
}