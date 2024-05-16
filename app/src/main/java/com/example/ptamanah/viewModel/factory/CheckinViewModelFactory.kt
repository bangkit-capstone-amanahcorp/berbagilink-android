package com.example.ptamanah.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ptamanah.data.repository.EventRepository
import com.example.ptamanah.viewModel.checkin.LogcheckinTenantViewModel

@Suppress("UNCHECKED_CAST")
class CheckinViewModelFactory(private val eventRepository: EventRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogcheckinTenantViewModel::class.java)) {
            return LogcheckinTenantViewModel(eventRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}