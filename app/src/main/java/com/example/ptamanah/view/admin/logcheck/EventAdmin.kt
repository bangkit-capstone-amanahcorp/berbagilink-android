package com.example.ptamanah.view.admin.logcheck

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.ptamanah.adapter.EventPagerAdminAdapter
import com.example.ptamanah.adapter.EventPagerAdminLogAdapter
import com.example.ptamanah.databinding.ActivityEventAdminBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class EventAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityEventAdminBinding
    private lateinit var adapter: EventPagerAdminLogAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = EventPagerAdminLogAdapter(supportFragmentManager, lifecycle)
        with(binding) {
            binding.pagerAdmin.adapter = adapter

            TabLayoutMediator(tabLayout, pagerAdmin) { tab, position ->
                when (position) {
                    0 -> tab.text = "Semua"
                    1 -> tab.text = "Berbayar"
                    2 -> tab.text = "Manual"
                }
            }.attach()
        }
    }
}


