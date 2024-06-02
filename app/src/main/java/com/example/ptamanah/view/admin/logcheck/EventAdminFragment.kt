package com.example.ptamanah.view.admin.logcheck

import EventAdminLogAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.preference.dataStore
import com.example.ptamanah.data.repository.CheckinRepository
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.FragmentEventAdminBinding
import com.example.ptamanah.viewModel.checkin.EventAdminViewModel
import com.example.ptamanah.viewModel.factory.CheckinViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EventAdminFragment : Fragment() {
    private lateinit var binding: FragmentEventAdminBinding
    private var position: Int? = null
    private var token: String? = ""
    private var event_id: String? = ""
    private val eventAdminViewModel: EventAdminViewModel by viewModels {
        CheckinViewModelFactory(getCheckinRepo())
    }
    private val userPreference: UserPreference by lazy { UserPreference(requireContext().dataStore) }
    private val eventAdapter by lazy { EventAdminLogAdapter("dummy_bangkit") }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            token = requireActivity().intent.getStringExtra(TOKEN)
            event_id = requireActivity().intent.getStringExtra(EVENT_ID)
            Log.d(TAG, "onViewCreated: $position & $token & $event_id")

            token?.let {
                when (position) {
                    1 -> showAllEvent()
                    2 -> showBerbayarEvent()
                    else -> showManualEvent()
                }
            } ?: Log.e(TAG, "Token is null")
        }


    }

    private fun showAllEvent() {
        setupRecyclerView()
        eventAdminViewModel.getCheckinLogAdmin(token.toString(), event_id.toString(), "", "", null)
            .observe(viewLifecycleOwner) { pagingData ->
                eventAdapter.submitData(lifecycle, pagingData)
                observeLoadState()
            }
    }

    private fun showBerbayarEvent() {
        setupRecyclerView()
        eventAdminViewModel.getCheckinLogAdmin(token.toString(), event_id.toString(), "", "", 0)
            .observe(viewLifecycleOwner) { pagingData ->
                eventAdapter.submitData(lifecycle, pagingData)
                observeLoadState()
            }
    }

    private fun showManualEvent() {
        setupRecyclerView()
        eventAdminViewModel.getCheckinLogAdmin(token.toString(), event_id.toString(), "", "", 1)
            .observe(viewLifecycleOwner) { pagingData ->
                    eventAdapter.submitData(lifecycle, pagingData)
                observeLoadState()
                }
    }

    private fun setupRecyclerView() {
        binding.rvEvent.layoutManager = LinearLayoutManager(activity)
        binding.rvEvent.adapter = eventAdapter
    }

    private fun getCheckinRepo(): CheckinRepository {
        val apiService = ApiConfig.getApiService()
        return CheckinRepository(apiService, userPreference)
    }

    private fun observeLoadState() {
        lifecycleScope.launch {
            eventAdapter.loadStateFlow.collectLatest { loadStates ->
                val isListEmpty = eventAdapter.itemCount == 0
                binding.tvEmpty.visibility = if (isListEmpty) View.VISIBLE else View.GONE
            }
        }
    }

    fun filteringEventsByStatus(status: String) {
        eventAdminViewModel.getCheckinLogAdmin(token.toString(), event_id.toString(), "", status, null)
            .observe(viewLifecycleOwner) { pagingData ->
                eventAdapter.submitData(lifecycle, pagingData)
                observeLoadState()
            }
    }

    companion object {
        private const val TAG = "EventAdminFragment"
        const val ARG_POSITION = "position"
        const val TOKEN = "token"
        const val EVENT_ID = "event_id"
    }
}
