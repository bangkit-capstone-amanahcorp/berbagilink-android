package com.example.ptamanah.viewModel.managementuser

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ptamanah.data.repository.ManagementUserRepository
import com.example.ptamanah.data.response.DataItemManagementUser
import com.example.ptamanah.data.response.ResponseChangePassword
import com.example.ptamanah.data.response.ResponseManagementUser
import com.example.ptamanah.data.response.ResponseUpdateUser
import com.example.ptamanah.data.response.Responsedelete
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ManagementUserViewModel (private val managementUserRepository: ManagementUserRepository) : ViewModel() {
    private val _managementUser = MutableLiveData<Result<ResponseManagementUser>>()

    private val _filteredData = MutableLiveData<List<DataItemManagementUser>>()
    val filteredData: LiveData<List<DataItemManagementUser>> = _filteredData

    fun getAllUser(token: String, rootView: View) {
        viewModelScope.launch {
            managementUserRepository.getManagementUser(token).collect { result ->
                result.onSuccess {
                    _managementUser.value = result
                    _filteredData.value = result.getOrNull()?.datamanagement?: emptyList()
                }
                result.onFailure {
                    Snackbar.make(rootView, "Silahkan perikan internet anda", Snackbar.LENGTH_SHORT).show()
                }
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

    suspend fun updateUser(token: String, id: Int, name: String, email: String, role: String): Flow<Result<ResponseUpdateUser>> {
        return managementUserRepository.updateUser(token, id, name, email, role)
    }

    suspend fun changePassword(token: String, id: Int, password: String, confirmPassword: String): Flow<Result<ResponseChangePassword>> {
        return managementUserRepository.changePassword(token, id, password, confirmPassword)
    }
}
