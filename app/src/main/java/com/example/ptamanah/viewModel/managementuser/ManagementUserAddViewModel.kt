package com.example.ptamanah.viewModel.managementuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ptamanah.data.repository.ManagementUserRepository
import com.example.ptamanah.data.response.ResponseManagementUserAdd
import kotlinx.coroutines.launch

class ManagementUserAddViewModel (private val repository: ManagementUserRepository): ViewModel() {

    private val _addResult = MutableLiveData<Result<ResponseManagementUserAdd>>()
    val addResult: LiveData<Result<ResponseManagementUserAdd>> get() = _addResult

    fun addManagementUser(token: String,name: String, password: String,passwordConfirmation: String, email: String,role: String) {
        viewModelScope.launch {
            repository.addUser(token,name, password,passwordConfirmation, email,role).collect {result ->
                _addResult.value = result
            }
        }
    }
}
