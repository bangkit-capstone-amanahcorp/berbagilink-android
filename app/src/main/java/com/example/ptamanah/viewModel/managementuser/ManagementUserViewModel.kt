package com.example.ptamanah.viewModel.managementuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ptamanah.data.repository.ManagementUserRepository
import com.example.ptamanah.data.response.DataItemManagementUser
import com.example.ptamanah.data.response.ResponseManagementUser
import com.example.ptamanah.data.response.Responsedelete
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ManagementUserViewModel (private val managementUserRepository: ManagementUserRepository) : ViewModel() {
    private val _managementUser = MutableLiveData<Result<ResponseManagementUser>>()
    val managementUser: LiveData<Result<ResponseManagementUser>> = _managementUser

    private val _filteredData = MutableLiveData<List<DataItemManagementUser>>()
    val filteredData: LiveData<List<DataItemManagementUser>> = _filteredData

    fun getAllUser(token: String) {
        viewModelScope.launch {
            managementUserRepository.getManagementUser(token).collect { result ->
                _managementUser.value = result
                _filteredData.value = result.getOrNull()?.datamanagement?: emptyList()
            }
        }
    }

    fun searchUser(query: String) {
        val searchResult = _managementUser.value?.getOrNull()?.datamanagement?.filter {
            it.name.contains(query, ignoreCase = true)
        } ?: emptyList()
        _filteredData.value = searchResult
    }
    suspend fun deleteUser(token: String, id: Int): Flow<Result<Responsedelete>> {
        return managementUserRepository.deleteUser(token, id)
    }
}
