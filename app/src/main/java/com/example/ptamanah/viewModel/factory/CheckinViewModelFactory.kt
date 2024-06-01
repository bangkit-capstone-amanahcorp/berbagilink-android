package com.example.ptamanah.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ptamanah.data.repository.CheckinRepository
import com.example.ptamanah.data.repository.EventRepository
import com.example.ptamanah.viewModel.checkin.EventAdminViewModel
import com.example.ptamanah.viewModel.checkin.LogcheckinCashierViewModel
import com.example.ptamanah.viewModel.checkin.LogcheckinTenantViewModel

@Suppress("UNCHECKED_CAST")
class CheckinViewModelFactory(private val checkinRepository: CheckinRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LogcheckinTenantViewModel::class.java) -> {
                LogcheckinTenantViewModel(checkinRepository) as T
            }
            modelClass.isAssignableFrom(LogcheckinCashierViewModel::class.java) -> {
                LogcheckinCashierViewModel(checkinRepository) as T
            }
            modelClass.isAssignableFrom(EventAdminViewModel::class.java) -> {
                EventAdminViewModel(checkinRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
