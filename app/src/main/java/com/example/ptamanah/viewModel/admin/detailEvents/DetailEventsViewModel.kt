package com.example.ptamanah.viewModel.admin.detailEvents

import androidx.lifecycle.ViewModel
import com.example.ptamanah.data.repository.EventRepository
import com.example.ptamanah.data.response.ResponseDetailEvents
import kotlinx.coroutines.flow.Flow

class DetailEventsViewModel(
    private val eventsRepository: EventRepository
) : ViewModel() {

    suspend fun getDetailEvent(token: String, id: String): Flow<Result<ResponseDetailEvents>> {
        return eventsRepository.eventDetail(token, id)
    }

    suspend fun getTiket(token: String, id: String): Flow<Result<ResponseDetailEvents>> {
        return eventsRepository.getTiket(token, id)
    }
}