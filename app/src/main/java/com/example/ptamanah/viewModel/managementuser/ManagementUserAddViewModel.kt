package com.example.ptamanah.viewModel.managementuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ptamanah.data.repository.ManagementUserRepository
import com.example.ptamanah.data.response.ResponseManagementUserAdd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class ManagementUserAddViewModel (private val repository: ManagementUserRepository): ViewModel() {

    private val _addResult = MutableStateFlow<Result<ResponseManagementUserAdd>?>(null)
    val addResult: StateFlow<Result<ResponseManagementUserAdd>?> = _addResult

    fun addManagementUser(
        token: String,
        name: String,
        email: String,
        password: String,
        passwordConfirmation: String,
        role: String
    ): Flow<Result<ResponseManagementUserAdd>> = flow {
        repository.addUser(token, name, password, passwordConfirmation, email, role).collect {
            _addResult.value = it
            emit(it)
        }
    }
}
