package com.example.ptamanah.view.admin.events

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.getevent.adapter.EventAdapter
import com.example.ptamanah.R
import com.example.ptamanah.adapter.EventAdminAdapter
import com.example.ptamanah.data.repository.EventRepository
import com.example.ptamanah.data.response.DataItem
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.FragmentListEventsAdminBinding
import com.example.ptamanah.databinding.FragmentMyEventBinding
import com.example.ptamanah.view.admin.logcheck.EventAdmin
import com.example.ptamanah.view.admin.logcheck.EventAdminFragment
import com.example.ptamanah.view.camera.CameraActivity
import com.example.ptamanah.view.myEvent.LogCheckinCashier
import com.example.ptamanah.view.myEvent.MyEventFragment
import com.example.ptamanah.viewModel.event.EventViewModel
import com.example.ptamanah.viewModel.factory.EventViewModelFactory
import kotlinx.coroutines.launch

class ListEventsAdminFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding: FragmentListEventsAdminBinding
    private var position: Int? = null
    private var token: String? =""
    private val eventViewModel: EventViewModel by viewModels {
        EventViewModelFactory(EventRepository(ApiConfig.getApiService()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListEventsAdminBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(MyEventFragment.ARG_POSITION)
            token = requireActivity().intent.getStringExtra(MyEventFragment.TOKEN)
            if (position == 1) {
                showAllEvent()
            } else if (position == 2) {
                showActiveEvent()
            } else {
                showDoneEvent()
            }
        }
    }

    private fun showAllEvent() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.rvEvent.layoutManager = linearLayoutManager
        val eventAdapter = EventAdminAdapter()
        binding.rvEvent.adapter = eventAdapter

        lifecycleScope.launch {
            eventViewModel.getAllEvent(token.toString()).collect { result ->
                result.onSuccess { response ->
                    binding.apply {
                        val sortedResponse = response.data?.sortedBy { it.saleStatus}
                        eventAdapter.submitList(sortedResponse)
                        if (sortedResponse.isNullOrEmpty()) {
                            binding.tvStatus.visibility = View.VISIBLE
                        }
                    }
                    showLoading(false)
                }
                result.onFailure {
                    showLoading(false)
                    Toast.makeText(
                        context,
                        "Silahkan periksa internet anda terlebih dahulu",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        eventAdapter.setOnItemClickCallBack(object : EventAdminAdapter.OnItemClickCallBack {
            override fun onItemClicked(user: DataItem) {
                Intent(context, CameraActivity::class.java).apply {
                    putExtra(CameraActivity.ID_EVENT, user.id)
                    putExtra(MyEventFragment.TOKEN, token)
                }.also {
                    startActivity(it)
                }
            }
        })
        eventAdapter.setOnDaftarClickCallBack(object : EventAdminAdapter.OnDaftarClickCallBack {
            override fun onDaftarClicked(user: DataItem) {
                Intent(context, EventAdmin::class.java).apply {
                    putExtra(EventAdminFragment.EVENT_ID, user.id)
                    putExtra(MyEventFragment.TOKEN, token)
                    Log.d("tesss2", "Navigating to Event Admin with eventId: ${user.id}")
                }.also {
                    startActivity(it)
                }
            }
        })
    }

    private fun showDoneEvent() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.rvEvent.layoutManager = linearLayoutManager
        val eventAdapter = EventAdminAdapter()
        binding.rvEvent.adapter = eventAdapter

        lifecycleScope.launch {
            eventViewModel.getAllEvent(token.toString()).collect { result ->
                result.onSuccess { response ->
                    binding.apply {
                        val sortedResponse = response.data?.filter { it.saleStatus == "end" }
                        eventAdapter.submitList(sortedResponse)
                        if (sortedResponse.isNullOrEmpty()) {
                            binding.tvStatus.visibility = View.VISIBLE
                        }
                    }
                    showLoading(false)
                }
                result.onFailure {
                    showLoading(false)
                    Toast.makeText(
                        context,
                        "Silahkan periksa internet anda terlebih dahulu",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        eventAdapter.setOnDaftarClickCallBack(object : EventAdminAdapter.OnDaftarClickCallBack {
            override fun onDaftarClicked(user: DataItem) {
                Intent(context, EventAdmin::class.java).apply {
                    putExtra(EventAdminFragment.EVENT_ID, user.id)
                    putExtra(MyEventFragment.TOKEN, token)
                    Log.d("tesss2", "Navigating to Event Admin with eventId: ${user.id}")
                }.also {
                    startActivity(it)
                }
            }
        })
    }

    private fun showActiveEvent() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.rvEvent.layoutManager = linearLayoutManager
        val eventAdapter = EventAdminAdapter()
        binding.rvEvent.adapter = eventAdapter

        lifecycleScope.launch {
            eventViewModel.getAllEvent(token.toString()).collect { result ->
                result.onSuccess { response ->
                    binding.apply {
                        val sortedResponse = response.data?.filter { it.saleStatus == "active" }
                        eventAdapter.submitList(sortedResponse)
                        if (sortedResponse.isNullOrEmpty()) {
                            binding.tvStatus.visibility = View.VISIBLE
                        }
                    }
                    showLoading(false)
                }
                result.onFailure {
                    showLoading(false)
                    Toast.makeText(
                        context,
                        "Silahkan periksa internet anda terlebih dahulu",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        eventAdapter.setOnItemClickCallBack(object : EventAdminAdapter.OnItemClickCallBack {
            override fun onItemClicked(user: DataItem) {
                Intent(context, CameraActivity::class.java).apply {
                    putExtra(CameraActivity.ID_EVENT, user.id)
                    putExtra(MyEventFragment.TOKEN, token)
                }.also {
                    startActivity(it)
                }
            }
        })
        eventAdapter.setOnDaftarClickCallBack(object : EventAdminAdapter.OnDaftarClickCallBack {
            override fun onDaftarClicked(user: DataItem) {
                Intent(context, EventAdmin::class.java).apply {
                    putExtra(EventAdminFragment.EVENT_ID, user.id)
                    putExtra(MyEventFragment.TOKEN, token)
                    Log.d("tesss2", "Navigating to Event Admin with eventId: ${user.id}")
                }.also {
                    startActivity(it)
                }
            }
        })
    }

    private fun showLoading(state: Boolean) {
        binding.pbLoading.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val ARG_POSITION = "position"
        const val TOKEN = "token"
    }
}