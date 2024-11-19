package com.example.ptamanah.viewModel.voucher

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ptamanah.data.repository.VoucherRepository
import com.example.ptamanah.data.response.voucher.ResponseVoucher
import kotlinx.coroutines.launch

class TambahVoucherViewModel(private val voucherRepository: VoucherRepository): ViewModel() {
    private val _addVoucherResult = MutableLiveData<Result<ResponseVoucher>>()
    val addVoucherResult: LiveData<Result<ResponseVoucher>> get() = _addVoucherResult

    fun addVoucher(
        token: String,
        name: String,
        code: String,
        type: String,
        feature: String,
        usage: String,
        value: Float,
        minValue: Int,
        maxValue: Int,
        quota: Int,
        used: Int,
        description: String,
        isActive: Boolean
    ) {
        viewModelScope.launch {
            val result = voucherRepository.addVoucher(
                token,
                name,
                code,
                type,
                feature,
                usage,
                value,
                minValue,
                maxValue,
                quota,
                used,
                description,
                isActive
            )
            _addVoucherResult.value = result
        }
    }
}