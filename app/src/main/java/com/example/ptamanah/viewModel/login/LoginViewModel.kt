package com.example.ptamanah.viewModel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.ptamanah.data.repository.AuthRepo
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepo: AuthRepo
) : ViewModel() {

    suspend fun userLogin(email: String, password: String) =
        authRepo.userLogin(email, password)

    suspend fun loginTenant(user: String, password: String, eventId: String) =
        authRepo.loginEvent(user, password, eventId)

    suspend fun checkEmail(email: String) =
        authRepo.checkEmail(email)

    fun saveAuthToken(token: String) {
        viewModelScope.launch {
            authRepo.saveAuthToken(token)
        }
    }

    fun saveSessionTenant(token: String) {
        viewModelScope.launch {
            authRepo.saveAuthTenant(token)
        }
    }



    fun getEmail(): LiveData<String?>
    {
        return authRepo.getEmail().asLiveData()
    }

    fun saveEmail(email: String) {
        viewModelScope.launch {
            authRepo.saveEmail(email)
        }
    }
}