package com.example.ptamanah.viewModel.managementuser

import androidx.lifecycle.ViewModel
import com.example.ptamanah.data.repository.ManagementUserRepository
import com.example.ptamanah.data.response.ResponseManagementUserAdd
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow

class ManagementUserAddViewModel (private val repository: ManagementUserRepository): ViewModel() {

    private val _addResult = MutableStateFlow<Result<ResponseManagementUserAdd>?>(null)

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
