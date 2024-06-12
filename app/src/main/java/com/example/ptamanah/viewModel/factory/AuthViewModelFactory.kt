package com.example.ptamanah.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ptamanah.data.repository.AuthRepo
import com.example.ptamanah.viewModel.event.EventTenantViewModel
import com.example.ptamanah.viewModel.login.LoginViewModel
import com.example.ptamanah.viewModel.main.MainViewModel
import com.example.ptamanah.viewModel.admin.mainadmin.HomePageAdminViewModel

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
            }
            modelClass.isAssignableFrom(HomePageAdminViewModel::class.java) -> {
                HomePageAdminViewModel(authRepo) as T
            } else-> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}