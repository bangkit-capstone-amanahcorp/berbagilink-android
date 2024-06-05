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
import androidx.paging.LoadState
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

class EventAdminFragment : Fragment(), OnCheckInSuccessListener {
    private lateinit var binding: FragmentEventAdminBinding
    private var position: Int? = null
    private var token: String? = ""
    private var event_id: String? = ""
    private var username: String? = ""
    private var currentStatus: String = ""
    private var currentManual: Int? = null
    private var dateStart: String? = null
    private var dateEnd: String? = null
    private var checkinTime: String? = null
    private val eventAdminViewModel: EventAdminViewModel by viewModels {
        CheckinViewModelFactory(getCheckinRepo())
    }
    private val userPreference: UserPreference by lazy { UserPreference(requireContext().dataStore) }
    private val eventAdapter by lazy {
        EventAdminLogAdapter(
            username.toString(),
            apiService = ApiConfig.getApiService(),
            token = token.toString(),
            this
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventAdminBinding.inflate(inflater, container, false)
        binding.tvEmpty.visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eventAdminViewModel.getUsername().observe(viewLifecycleOwner) {
            username = it.toString()

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

    }

    private fun showAllEvent() {
        setupRecyclerView()
        eventAdminViewModel.getCheckinLogAdmin(token.toString(), event_id.toString(), "", "", null, dateStart, dateEnd,checkinTime)
            .observe(viewLifecycleOwner) { pagingData ->
                eventAdapter.submitData(lifecycle, pagingData)
                observeLoadState()
            }
    }

    private fun showBerbayarEvent() {
        setupRecyclerView()
        setCurrentManual(1)
        eventAdminViewModel.getCheckinLogAdmin(token.toString(), event_id.toString(), "", "", getCurrentManualFilter(),dateStart,dateEnd,checkinTime)
            .observe(viewLifecycleOwner) { pagingData ->
                eventAdapter.submitData(lifecycle, pagingData)
                observeLoadState()
            }
    }


    private fun showManualEvent() {
        setupRecyclerView()
        setCurrentManual(0)
        eventAdminViewModel.getCheckinLogAdmin(token.toString(), event_id.toString(), "", "", getCurrentManualFilter(),dateStart,dateEnd,checkinTime)
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
            eventAdapter.loadStateFlow.collectLatest {
                val isLoading = it.refresh is LoadState.Loading
                showLoading(isLoading)

                val isListEmpty = it.refresh is LoadState.NotLoading && eventAdapter.itemCount == 0
                binding.tvEmpty.visibility = if (isListEmpty) View.VISIBLE else View.GONE
            }
        }
    }

    fun filteringEventsByStatus(status: String) {
        eventAdminViewModel.getCheckinLogAdmin(token.toString(), event_id.toString(), "", status, null,dateStart,dateEnd,checkinTime)
            .observe(viewLifecycleOwner) { pagingData ->
                eventAdapter.submitData(lifecycle, pagingData)
                observeLoadState()
            }
    }

    fun performSearch(query: String,startDate: String? = dateStart, endDate: String? = dateEnd, checkinTime: String? = this.checkinTime) {
        val currentStatus = getCurrentStatusFilter()
        val isManual = getCurrentManualFilter()
        eventAdminViewModel.getCheckinLogAdmin(token.toString(), event_id.toString(), query, currentStatus,  isManual, startDate, endDate,checkinTime)
            .observe(viewLifecycleOwner) { pagingData ->
                eventAdapter.submitData(lifecycle, pagingData)
                observeLoadState()
            }
    }

    fun setCurrentStatus(status: String) {
        currentStatus = status
    }

    private fun getCurrentStatusFilter(): String {
        return currentStatus
    }

    fun setCurrentManual(isManual: Int?) {
        currentManual = isManual
    }

    private fun getCurrentManualFilter(): Int? {
        return currentManual
    }

    private fun showLoading(state: Boolean) {
        binding.pbLoading.visibility = if (state) View.VISIBLE else View.GONE
    }


    override fun onCheckInSuccess() {
        loadData()
    }

    private fun loadData() {
        when (position) {
            1 -> showAllEvent()
            2 -> showBerbayarEvent()
            else -> showManualEvent()
        }
    }

    companion object {
        private const val TAG = "EventAdminFragment"
        const val ARG_POSITION = "position"
        const val TOKEN = "token"
        const val EVENT_ID = "event_id"
    }
    fun newInstance(position: Int): EventAdminFragment {
        val fragment = EventAdminFragment()
        val args = Bundle()
        args.putInt(ARG_POSITION, position)
        fragment.arguments = args
        return fragment
    }
}
