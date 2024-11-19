package com.example.ptamanah.viewModel.voucher

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ptamanah.data.repository.VoucherRepository
import com.example.ptamanah.data.response.voucher.ResponseListVoucher
import kotlinx.coroutines.launch

class VoucherViewModel(
    private val voucherRepository: VoucherRepository
): ViewModel() {
    private val _vouchers = MutableLiveData<Result<ResponseListVoucher>>()
    val vouchers: LiveData<Result<ResponseListVoucher>> get() = _vouchers

    fun getAllVoucher(token: String) {
        viewModelScope.launch {
            val result = voucherRepository.getVoucher(token)
            _vouchers.value = result
            Log.e("data", result.toString())
        }
    }
}