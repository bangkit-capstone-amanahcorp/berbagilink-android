package com.example.ptamanah.viewModel.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ptamanah.data.repository.AuthRepo
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepo: AuthRepo
) : ViewModel() {

    suspend fun userLogin(email: String, password: String) =
        authRepo.userLogin(email, password)

    fun saveAuthToken(token: String) {
        viewModelScope.launch {
            authRepo.saveAuthToken(token)
        }
    }
}