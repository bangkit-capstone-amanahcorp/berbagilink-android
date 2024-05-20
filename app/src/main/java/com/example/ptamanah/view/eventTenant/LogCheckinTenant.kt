package com.example.ptamanah.view.eventTenant

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ptamanah.adapter.CheckinAdapter
import com.example.ptamanah.data.repository.EventRepository
import com.example.ptamanah.data.response.DataItemtenant
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityLogCheckinTenantBinding
import com.example.ptamanah.viewModel.checkin.LogcheckinTenantViewModel
import com.example.ptamanah.viewModel.factory.CheckinViewModelFactory

class LogCheckinTenant : AppCompatActivity() {
    private lateinit var binding: ActivityLogCheckinTenantBinding
    private val checkinViewModel: LogcheckinTenantViewModel by viewModels {
        CheckinViewModelFactory(getCheckinRepo())
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogCheckinTenantBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    val query = searchView.text.toString()
                    searchBar.setText(query)
                    searchView.hide()
                    checkinViewModel.searchUser(query)
                    false
                }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val layoutManager = LinearLayoutManager(this)
        binding.rvReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvReview.addItemDecoration(itemDecoration)

        checkinViewModel.filteredCheckinData.observe(this) { result ->
            setUserList(result)
        }

        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2NsYmtsaW5rLmJlcmJhZ2kubGluay9hcGkvZXZlbnQtbW9iaWxlL3RlbmFudC9sb2dpbiIsImlhdCI6MTcxNTc0MzIzNCwiZXhwIjoxNzE4MzcxMjM0LCJuYmYiOjE3MTU3NDMyMzQsImp0aSI6ImxudzQ5UEo0VHZwOThUSkYiLCJzdWIiOjgxLCJwcnYiOiJhOTE0ZTk1MTFmOTIyNDRlNGQ0MTRiZGY0MmUyNDE3MmQ1ZWZhOTc4In0.y_Nphy1gn1jzpKIE8NF9Nm8LRKFETjdFEdM2UWLRpDI"
        checkinViewModel.getAllCheckins(token)
    }
    private fun setUserList(data : List<DataItemtenant>) {
        val adapter = CheckinAdapter(data)
        binding.rvReview.adapter = adapter
    }
    private fun getCheckinRepo(): EventRepository {
        val apiService = ApiConfig.getApiService()
        return EventRepository(apiService)
    }
}