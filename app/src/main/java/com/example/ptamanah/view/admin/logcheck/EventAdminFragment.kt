package com.example.ptamanah.view.admin.logcheck

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ptamanah.adapter.EventAdminLogAdapter
import com.example.ptamanah.data.repository.CheckinRepository
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.FragmentEventAdminBinding
import com.example.ptamanah.viewModel.checkin.EventAdminViewModel
import com.example.ptamanah.viewModel.factory.CheckinViewModelFactory

class EventAdminFragment : Fragment() {
    private lateinit var binding: FragmentEventAdminBinding
    private var position: Int? = null
    private var token: String? = ""
    private val eventAdminViewModel: EventAdminViewModel by viewModels {
        CheckinViewModelFactory(getCheckinRepo())
    }
    private val eventAdapter by lazy { EventAdminLogAdapter() }

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
            token?.let {
                when (position) {
                    1 -> showAllEvent()
                    2 -> showActiveEvent()
                    else -> showDoneEvent()
                }
            } ?: Log.e(TAG, "Token is null")
        }
    }

    private fun showAllEvent() {
        setupRecyclerView()
        eventAdminViewModel.getCheckinLogAdmin(token!!, "", null, "", 0)
            .observe(viewLifecycleOwner) { pagingData ->
                eventAdapter.submitData(lifecycle, pagingData)
            }
    }

    private fun showDoneEvent() {
        setupRecyclerView()
        eventAdminViewModel.getCheckinLogAdmin(token!!, "", null, "", 0)
            .observe(viewLifecycleOwner) { pagingData ->
                eventAdapter.submitData(lifecycle, pagingData)
            }
    }

    private fun showActiveEvent() {
        setupRecyclerView()
        eventAdminViewModel.getCheckinLogAdmin(token!!, "", null, "", 1)
            .observe(viewLifecycleOwner) { pagingData ->
                eventAdapter.submitData(lifecycle, pagingData)
            }
    }

    private fun setupRecyclerView() {
        binding.rvEvent.layoutManager = LinearLayoutManager(activity)
        binding.rvEvent.adapter = eventAdapter
    }

    private fun getCheckinRepo(): CheckinRepository {
        val apiService = ApiConfig.getApiService()
        return CheckinRepository(apiService)
    }

    companion object {
        private const val TAG = "EventAdminFragment"
        const val ARG_POSITION = "position"
        const val TOKEN = "token"
    }
}
