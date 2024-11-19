package com.example.ptamanah.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ptamanah.data.repository.VoucherRepository
import com.example.ptamanah.viewModel.voucher.TambahVoucherViewModel
import com.example.ptamanah.viewModel.voucher.VoucherViewModel

class VoucherViewModelFactory(
    private val voucherRepository: VoucherRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(VoucherViewModel::class.java) -> {
                VoucherViewModel(voucherRepository) as T
            }
            modelClass.isAssignableFrom(TambahVoucherViewModel::class.java) -> {
                TambahVoucherViewModel(voucherRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}