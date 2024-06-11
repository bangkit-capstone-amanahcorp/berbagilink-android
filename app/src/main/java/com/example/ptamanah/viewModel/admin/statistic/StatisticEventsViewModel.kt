package com.example.ptamanah.viewModel.admin.statistic

import androidx.lifecycle.ViewModel
import com.example.ptamanah.data.repository.EventRepository
import com.example.ptamanah.data.response.ResponseReportStatistic
import kotlinx.coroutines.flow.Flow

class StatisticEventsViewModel(
    private val eventRepository: EventRepository
) : ViewModel(){

    suspend fun getStatisticEvents(token: String, id: String): Flow<Result<ResponseReportStatistic>> {
        return eventRepository.getReportStatistic(token, id)
    }
}