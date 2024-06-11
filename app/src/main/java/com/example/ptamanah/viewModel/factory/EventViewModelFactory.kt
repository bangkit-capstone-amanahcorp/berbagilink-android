package com.example.ptamanah.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ptamanah.data.repository.EventRepository
import com.example.ptamanah.viewModel.admin.detailEvents.DetailEventsViewModel
import com.example.ptamanah.viewModel.admin.statistic.StatisticEventsViewModel
import com.example.ptamanah.viewModel.event.EventViewModel

@Suppress("UNCHECKED_CAST")
class EventViewModelFactory(
    private val eventRepository: EventRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(EventViewModel::class.java) -> {
                EventViewModel(eventRepository) as T
            }
            modelClass.isAssignableFrom(DetailEventsViewModel::class.java) -> {
                DetailEventsViewModel(eventRepository) as T
            }
            modelClass.isAssignableFrom(StatisticEventsViewModel::class.java) -> {
                StatisticEventsViewModel(eventRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }
}