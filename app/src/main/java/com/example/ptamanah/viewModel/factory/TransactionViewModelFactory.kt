package com.example.ptamanah.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ptamanah.data.repository.TransactionRepository
import com.example.ptamanah.viewModel.admin.transaction.TransactionEventsViewModel

class TransactionViewModelFactory (private val transactionRepository: TransactionRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionEventsViewModel::class.java)) {
            return TransactionEventsViewModel(transactionRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}