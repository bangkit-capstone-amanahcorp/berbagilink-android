package com.example.ptamanah.view.myEvent

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ptamanah.R
import com.example.ptamanah.adapter.CheckinAdapterChasier
import com.example.ptamanah.adapter.CheckinAdapterTenant
import com.example.ptamanah.adapter.LoadingStateAdapter
import com.example.ptamanah.data.repository.CheckinRepository
import com.example.ptamanah.data.repository.EventRepository
import com.example.ptamanah.data.response.DataItemCashier
import com.example.ptamanah.data.response.DataItemtenant
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityLogCheckinCashierBinding
import com.example.ptamanah.databinding.ActivityLogCheckinTenantBinding
import com.example.ptamanah.viewModel.checkin.LogcheckinCashierViewModel
import com.example.ptamanah.viewModel.checkin.LogcheckinTenantViewModel
import com.example.ptamanah.viewModel.factory.CheckinViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LogCheckinCashier : AppCompatActivity() {
    private lateinit var binding: ActivityLogCheckinCashierBinding
    private val checkinViewModel: LogcheckinCashierViewModel by viewModels {
        CheckinViewModelFactory(getCheckinRepo())
    }
    private val checkinAdapter = CheckinAdapterChasier()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogCheckinCashierBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentView(binding.root)

        setupActionBar()
        setupRecyclerView()
        setupSearchView()

        val token = intent.getStringExtra(TOKEN).toString()
        val eventId = intent.getStringExtra(ID_EVENT).toString()

        Log.d("masok", "Received token: $token")
        Log.d("masok", "Received eventId: $eventId")

        // Observe the LiveData from ViewModel
        checkinViewModel.getCheckinscashier(token, eventId).observe(this) { pagingData ->
            handleSearchResults(pagingData)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupRecyclerView() {
        binding.rvReview.layoutManager = LinearLayoutManager(this)
        binding.rvReview.adapter = checkinAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                checkinAdapter.retry()
            }
        )
        binding.rvReview.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                checkinViewModel.searchUsercashier(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                checkinViewModel.searchUsercashier(newText)
                return false
            }
        })
    }

    private fun handleSearchResults(pagingData: PagingData<DataItemCashier>) {
        lifecycleScope.launch {
            checkinAdapter.submitData(pagingData)
            checkinAdapter.loadStateFlow.collectLatest { loadStates ->
                val isListEmpty = checkinAdapter.itemCount == 0
                binding.NotfoundTv.isVisible = isListEmpty
                Log.d("logkasiractv", "Adapter item count: ${checkinAdapter.itemCount}")
                if (isListEmpty) {
                    Log.d("knapaLogCheckinCashier", "No data found")
                }
            }
        }
    }

    private fun getCheckinRepo(): CheckinRepository {
        val apiService = ApiConfig.getApiService()
        return CheckinRepository(apiService)
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    androidx.cardview.R.color.cardview_light_background
                )
            )
        )
        val customActionBar = LayoutInflater.from(this).inflate(R.layout.actionbar_list_log_checkin_tenant, null)
        val actionBarParams = ActionBar.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT
        )
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(customActionBar, actionBarParams)
    }

    companion object {
        const val ID_EVENT = "id_event"
        const val TOKEN = "token"
    }
}
