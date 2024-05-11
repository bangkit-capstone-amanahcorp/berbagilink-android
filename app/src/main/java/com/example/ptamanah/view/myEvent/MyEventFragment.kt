package com.example.ptamanah.view.myEvent

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
import com.example.ptamanah.data.repository.EventRepository
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.FragmentMyEventBinding
import com.example.ptamanah.viewModel.event.EventViewModel
import com.example.ptamanah.viewModel.factory.EventViewModelFactory
import kotlinx.coroutines.launch

class MyEventFragment : Fragment() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var binding: FragmentMyEventBinding
    private var position: Int? = null
    private var token : String? = ""

    private val eventViewModel: EventViewModel by viewModels {
        EventViewModelFactory(EventRepository(ApiConfig.getApiService()))
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyEventBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)

            token = requireActivity().intent.getStringExtra(TOKEN)

            if (position == 1) {
                showActiveEvent()
            } else {
                showDoneEvent()
            }
        }
    }

    private fun showDoneEvent() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.rvEvent.layoutManager = linearLayoutManager
        val eventAdapter = EventAdapter()
        binding.rvEvent.adapter = eventAdapter

        lifecycleScope.launch {
            eventViewModel.getAllEvent(token.toString()).collect { result ->
                Log.d("tokenNow", token.toString())
                Log.d("resultnyaEnd", result.toString())
                result.onSuccess { response ->
                    binding.apply {
                        val sortedResponse = response.data?.filter { it.saleStatus == "end"}
                        eventAdapter.submitList(sortedResponse)
                        if (sortedResponse.isNullOrEmpty()) {
                            binding.tvStatus.visibility = View.VISIBLE
                        } else {

                        }
                        Log.d("endBrok", sortedResponse.toString())
                    }

                    showLoading(false)
                }
                result.onFailure {
                    Log.d("resultnya2", result.toString())
                    showLoading(false)
                    Toast.makeText(context, "Gagal dapat data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showActiveEvent() {
        linearLayoutManager = LinearLayoutManager(activity)
        binding.rvEvent.layoutManager = linearLayoutManager
        val eventAdapter = EventAdapter()
        binding.rvEvent.adapter = eventAdapter

        lifecycleScope.launch {
            eventViewModel.getAllEvent(token.toString()).collect { result ->
                Log.d("tokenNow", token.toString())
                Log.d("resultnya1", result.toString())
                result.onSuccess { response ->
                    binding.apply {
                        val sortedResponse = response.data?.filter { it.saleStatus == "active"}
                        Log.d("responsee", sortedResponse.toString())
                        eventAdapter.submitList(sortedResponse)
                        if (sortedResponse.isNullOrEmpty()) {
                            binding.tvStatus.visibility = View.VISIBLE
                        }
                    }
                    showLoading(false)
                }
                result.onFailure {
                    Log.d("resultnya2", result.toString())
                    showLoading(false)
                    Toast.makeText(context, "Gagal dapat data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    private fun showLoading(state: Boolean) { binding.pbLoading.visibility = if (state) View.VISIBLE else View.GONE }



    companion object {
        const val ARG_POSITION = "position"
        const val TOKEN = "token"
    }
}