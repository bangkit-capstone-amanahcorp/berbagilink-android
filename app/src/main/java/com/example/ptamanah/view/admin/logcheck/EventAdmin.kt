package com.example.ptamanah.view.admin.logcheck

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.ptamanah.adapter.EventPagerAdminAdapter
import com.example.ptamanah.adapter.EventPagerAdminLogAdapter
import com.example.ptamanah.databinding.ActivityEventAdminBinding
import com.example.ptamanah.view.eventTenant.BottomLogin
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class EventAdmin : AppCompatActivity(), FilteringStatus.OnFilterSelectedListener {

    private lateinit var binding: ActivityEventAdminBinding
    private lateinit var adapter: EventPagerAdminLogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    }

    override fun onFilterSelected(status: String) {
        val currentFragment = supportFragmentManager.findFragmentByTag("f" + binding.pagerAdmin.currentItem)
        if (currentFragment is EventAdminFragment) {
            currentFragment.filteringEventsByStatus(status)
        }
        if (status == "") {
            binding.btnFilter.text = "Semua"
        } else {
            binding.btnFilter.text = status
        }
    }
}


