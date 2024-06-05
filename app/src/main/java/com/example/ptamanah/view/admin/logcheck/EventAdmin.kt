package com.example.ptamanah.view.admin.logcheck

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.ptamanah.adapter.EventPagerAdminAdapter
import com.example.ptamanah.adapter.EventPagerAdminLogAdapter
import com.example.ptamanah.databinding.ActivityEventAdminBinding
import com.example.ptamanah.view.eventTenant.BottomLogin
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class EventAdmin : AppCompatActivity(), FilteringStatus.OnFilterSelectedListener {

    private lateinit var binding: ActivityEventAdminBinding
    private lateinit var adapter: EventPagerAdminLogAdapter
    private var dateStart: String? = null
    private var dateEnd: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnDateRange.setOnClickListener{
            showDateRangePicker()
        }

        binding.btnFilter.setOnClickListener {
            val bottomShet = FilteringStatus()
            bottomShet.setOnFilterSelectedListener(this)
            bottomShet.show(supportFragmentManager, BottomLogin.TAG)
        }

        val viewPager: ViewPager2 = binding.pagerAdmin
        val eventPagerAdminLogAdapter = EventPagerAdminLogAdapter(this)

        viewPager.adapter = eventPagerAdminLogAdapter
        val tabLayout: TabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Semua"
                1 -> tab.text = "Berbayar"
                2 -> tab.text = "Manual"
            }
        }.attach()

        setupSearchView()
    }

    override fun onFilterSelected(status: String) {
        val currentFragment = supportFragmentManager.findFragmentByTag("f" + binding.pagerAdmin.currentItem)
        if (currentFragment is EventAdminFragment) {
            currentFragment.setCurrentStatus(status)
            currentFragment.filteringEventsByStatus(status)
        }
        if (status == "") {
            binding.btnFilter.text = "Semua Status"
        } else {
            binding.btnFilter.text = status
        }
    }
    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { performSearch(it) }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { performSearch(it) }
                return false
            }
        })
    }

    private fun performSearch(query: String) {
        val currentFragment = supportFragmentManager.findFragmentByTag("f" + binding.pagerAdmin.currentItem)
        if (currentFragment is EventAdminFragment) {
            currentFragment.performSearch(query)
        }
    }

    private fun showDateRangePicker() {
        val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText("Select Date Range")
            .build()

        dateRangePicker.show(supportFragmentManager, "date_range_picker")

        dateRangePicker.addOnPositiveButtonClickListener { selection ->
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            val startDate = dateFormat.format(Date(selection.first ?: 0))
            val endDate = dateFormat.format(Date(selection.second ?: 0))

            dateStart = startDate
            dateEnd = endDate

            binding.btnDateRange.text = "$startDate - $endDate"

            // Trigger data fetch with the selected date range
            val currentFragment = supportFragmentManager.findFragmentByTag("f" + binding.pagerAdmin.currentItem)
            if (currentFragment is EventAdminFragment) {
                currentFragment.performSearch(query = "", startDate, endDate)
            }
        }
    }
}