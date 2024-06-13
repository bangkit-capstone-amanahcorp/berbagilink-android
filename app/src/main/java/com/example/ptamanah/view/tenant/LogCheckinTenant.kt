package com.example.ptamanah.view.tenant

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ptamanah.R
import com.example.ptamanah.adapter.CheckinAdapterTenant
import com.example.ptamanah.adapter.LoadingStateAdapter
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.preference.dataStore
import com.example.ptamanah.data.repository.CheckinRepository
import com.example.ptamanah.data.response.DataItemtenant
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityLogCheckinTenantBinding
import com.example.ptamanah.viewModel.checkin.LogcheckinTenantViewModel
import com.example.ptamanah.viewModel.factory.CheckinViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LogCheckinTenant : AppCompatActivity() {
    private lateinit var binding: ActivityLogCheckinTenantBinding
    private val checkinViewModel: LogcheckinTenantViewModel by viewModels {
        CheckinViewModelFactory(getCheckinRepo())
    }
    private val userPreference: UserPreference by lazy { UserPreference(this.dataStore) }
    private val checkinAdapter = CheckinAdapterTenant()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogCheckinTenantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        setupRecyclerView()
        setupSearchView()

        val token = intent.getStringExtra(TOKEN_ID).toString()
        val eventId = intent.getStringExtra(ID_EVENT_TENANT).toString()

        checkinViewModel.getCheckins(token, eventId).observe(this) { pagingData ->
            handleSearchResults(pagingData)
        }

        binding.swipeRefresh.setOnRefreshListener {
            checkinViewModel.getCheckins(token, eventId).observe(this) { pagingData ->
                handleSearchResults(pagingData)
            }
            binding.swipeRefresh.isRefreshing = false
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
                checkinViewModel.searchUser(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                checkinViewModel.searchUser(newText)
                return false
            }
        })
    }

    private fun handleSearchResults(pagingData: PagingData<DataItemtenant>) {
        lifecycleScope.launch {
            checkinAdapter.submitData(pagingData)
            checkinAdapter.loadStateFlow.collectLatest {
                val isListEmpty = checkinAdapter.itemCount == 0
                binding.NotfoundTv.isVisible = isListEmpty
            }
        }
    }

    private fun getCheckinRepo(): CheckinRepository {
        val apiService = ApiConfig.getApiService()
        return CheckinRepository(apiService, userPreference)
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
        const val TOKEN_ID = "TOKEN"
        const val ID_EVENT_TENANT = "idEvent"
    }
}
