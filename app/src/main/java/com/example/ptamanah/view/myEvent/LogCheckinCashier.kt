package com.example.ptamanah.view.myEvent

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ptamanah.R
import com.example.ptamanah.adapter.CheckinAdapter
import com.example.ptamanah.data.repository.EventRepository
import com.example.ptamanah.data.response.DataItemtenant
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityLogCheckinTenantBinding
import com.example.ptamanah.viewModel.checkin.LogcheckinTenantViewModel
import com.example.ptamanah.viewModel.factory.CheckinViewModelFactory

class LogCheckinCashier : AppCompatActivity() {
//    private lateinit var binding: ActivityLogCheckinTenantBinding
//    private val checkinViewModel: LogcheckinTenantViewModel by viewModels {
//        CheckinViewModelFactory(getCheckinRepo())
//    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityLogCheckinTenantBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setupActionBar()
//
//        val layoutManager = LinearLayoutManager(this)
//        binding.rvReview.layoutManager = layoutManager
//        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                query?.let {
//                    checkinViewModel.searchUser(it)
//                }
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                newText?.let {
//                    checkinViewModel.searchUser(it)
//                }
//                return false
//            }
//        })
//
//        checkinViewModel.filteredCheckinData.observe(this) { result ->
//            setUserList(result)
//            binding.NotfoundTv.isVisible = result.isEmpty()
//        }
//
//        val token = intent.getStringExtra(TOKEN_ID).toString()
//        val eventId = intent.getStringExtra(ID_EVENT_TENANT).toString()
//        checkinViewModel.getAllCheckins(token, eventId)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> {
//                finish()
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    private fun setUserList(data: List<DataItemtenant>) {
//        val adapter = CheckinAdapter(data)
//        binding.rvReview.adapter = adapter
//    }
//
//    private fun getCheckinRepo(): EventRepository {
//        val apiService = ApiConfig.getApiService()
//        return EventRepository(apiService)
//    }
//
//    private fun setupActionBar() {
//        supportActionBar?.setDisplayShowTitleEnabled(false)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setBackgroundDrawable(
//            ColorDrawable(
//                ContextCompat.getColor(
//                    this,
//                    androidx.cardview.R.color.cardview_light_background
//                )
//            )
//        )
//        val customActionBar = LayoutInflater.from(this).inflate(R.layout.actionbar_list_log_checkin_tenant, null)
//        val actionBarParams = ActionBar.LayoutParams(
//            ActionBar.LayoutParams.MATCH_PARENT,
//            ActionBar.LayoutParams.MATCH_PARENT
//        )
//        supportActionBar?.setDisplayShowCustomEnabled(true)
//        supportActionBar?.setCustomView(customActionBar, actionBarParams)
//    }
//
//    companion object {
//        const val TOKEN_ID = "TOKEN"
//        const val ID_EVENT_TENANT = "idEvent"
//    }
}