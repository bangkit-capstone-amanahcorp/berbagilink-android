package com.example.ptamanah.viewModel.scan

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ptamanah.data.repository.ScanRepo
import com.example.ptamanah.data.response.ResponseScan
import kotlinx.coroutines.flow.Flow

class ScanViewModel(
    private val scan: ScanRepo
) : ViewModel() {
    suspend fun scanEvent(auth: String, id: String, token: String): Flow<Result<ResponseScan>> {
        Log.d("why", "iiio")
        return scan.scanEvent(auth, id,  token)
    }
}
