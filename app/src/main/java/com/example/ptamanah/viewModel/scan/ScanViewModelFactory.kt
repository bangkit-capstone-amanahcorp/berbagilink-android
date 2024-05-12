package com.example.ptamanah.viewModel.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ptamanah.data.repository.ScanRepo


class ScanViewModelFactory(private val scanRepo: ScanRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(ScanViewModel::class.java) -> {
                ScanViewModel(scanRepo) as T
            } else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}