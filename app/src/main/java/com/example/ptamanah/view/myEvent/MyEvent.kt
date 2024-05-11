package com.example.ptamanah.view.myEvent

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
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
import com.example.ptamanah.view.main.MainActivity
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

        setupActionBar()

        token = intent.getStringExtra(TOKEN)
        getAdapter()
        getEvent()

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

    private fun setupActionBar() {
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, androidx.cardview.R.color.cardview_light_background)))

        val customActionBar = LayoutInflater.from(this).inflate(R.layout.actionbar_event, null)
        val actionBarParams = ActionBar.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT
        )
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(customActionBar, actionBarParams)
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