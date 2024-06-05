package com.example.ptamanah.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ptamanah.data.repository.CheckinRepository
import com.example.ptamanah.data.repository.ManagementUserRepository
import com.example.ptamanah.viewModel.event.EventViewModel
import com.example.ptamanah.viewModel.managementuser.ManagementUserViewModel

@Suppress("UNCHECKED_CAST")
class ManagementUserViewModelFactory(private val managementUserRepository: ManagementUserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ManagementUserViewModel::class.java) -> {
                ManagementUserViewModel(managementUserRepository) as T
            } else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}