package com.example.ptamanah.view.myEvent

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.getevent.adapter.EventAdapter
import com.bangkit.getevent.viewModel.EventViewModel
import com.bangkit.getevent.viewModel.EventViewModelFactory
import com.example.ptamanah.R
import com.example.ptamanah.data.repository.EventRepository
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityMyEventBinding
import kotlinx.coroutines.launch

class MyEvent : AppCompatActivity() {
    private lateinit var binding: ActivityMyEventBinding
    private var token : String? = ""
    private val viewModel: EventViewModel by viewModels {
        EventViewModelFactory(EventRepository(ApiConfig.getApiService()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra(TOKEN)
        getAdapter()
        getEvent()

        binding.backToHome.setOnClickListener {
            finish()
        }

    }

    private fun getAdapter() {

        val layoutManager = LinearLayoutManager(this)
        binding.rvEvent.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvEvent.addItemDecoration(itemDecoration)
    }

    private fun getEvent() {
        showLoading(true)
        val adapter = EventAdapter()
        binding.rvEvent.adapter = adapter

        lifecycleScope.launch {
            viewModel.getAllEvent(token.toString()).collect { result ->
                Log.d("tokenNow", token.toString())
                Log.d("resultnya1", result.toString())
                result.onSuccess { response ->
                    binding.apply {
                        adapter.submitList(response.data)
                    }
                    showLoading(false)
                }
                result.onFailure {
                    Log.d("resultnya2", result.toString())
                    showLoading(false)
                    Toast.makeText(this@MyEvent, "Gagal dapat data", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val TOKEN = "token"
    }
}