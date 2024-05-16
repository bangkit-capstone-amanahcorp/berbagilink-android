package com.example.ptamanah.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ptamanah.data.repository.AuthRepo
import com.example.ptamanah.viewModel.event.EventTenantViewModel
import com.example.ptamanah.viewModel.event.EventViewModel
import com.example.ptamanah.viewModel.login.LoginViewModel
import com.example.ptamanah.viewModel.main.MainViewModel

class AuthViewModelFactory(private val authRepo: AuthRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(authRepo) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(authRepo) as T
            }
            modelClass.isAssignableFrom(EventTenantViewModel::class.java) -> {
                EventTenantViewModel(authRepo) as T
            }else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}