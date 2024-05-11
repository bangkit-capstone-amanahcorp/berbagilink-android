package com.example.ptamanah.view.myEvent

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.getevent.adapter.EventAdapter
import com.example.ptamanah.viewModel.event.EventViewModel
import com.example.ptamanah.viewModel.factory.EventViewModelFactory
import com.example.ptamanah.R
import com.example.ptamanah.adapter.EventPagerAdapter
import com.example.ptamanah.data.repository.EventRepository
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityMyEventBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class MyEvent : AppCompatActivity() {
    private lateinit var binding: ActivityMyEventBinding
    private val viewModel: EventViewModel by viewModels {
        EventViewModelFactory(EventRepository(ApiConfig.getApiService()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        /*token = intent.getStringExtra(TOKEN)
        getAdapter()
        getEvent()*/
        setViewPager()

    }

    private fun setViewPager() {
        val viewPager: ViewPager2 = binding.viewPager
        val tabs: TabLayout = binding.tabs
        val eventPagerAdapter = EventPagerAdapter(this)

        viewPager.adapter = eventPagerAdapter

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
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

    /*private fun getAdapter() {

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
    }*/

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        private val TAB_TITLES = intArrayOf(
            R.string.event_aktif,
            R.string.event_selesai
        )
    }
}