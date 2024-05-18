package com.example.ptamanah.viewModel.scan

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.ptamanah.data.repository.ScanRepo
import com.example.ptamanah.data.response.ResponseScan
import com.example.ptamanah.data.response.ResponseScanTenant
import kotlinx.coroutines.flow.Flow

class ScanViewModel(
    private val scan: ScanRepo
) : ViewModel() {
    suspend fun scanEvent(auth: String, id: String, token: String): Flow<Result<ResponseScan>> {
        Log.d("why", "iiio")
        return scan.scanEvent(auth, id,  token)
    }

    fun getTokenTenant():LiveData<String?> {
        return scan.getSessionTenant().asLiveData()
    }

    suspend fun scanTenant(auth: String, id: String, code: String): Flow<Result<ResponseScanTenant>> {
        return scan.scanTenant(auth, id, code)
    }
}
