package com.example.ptamanah.viewModel.event

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ptamanah.data.repository.EventRepository
import com.example.ptamanah.data.response.ResponseListEvent
import kotlinx.coroutines.flow.Flow

class EventViewModel(
    private val eventRepository: EventRepository
) : ViewModel() {

    suspend fun getAllEvent(token: String): Flow<Result<ResponseListEvent>> {

        Log.d("ResponseCuy1", "hi")
        return eventRepository.getEvent(token)
    }
}