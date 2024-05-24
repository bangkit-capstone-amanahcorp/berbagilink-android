package com.example.ptamanah.view.myEvent

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.ptamanah.R
import com.example.ptamanah.adapter.EventPagerAdapter
import com.example.ptamanah.databinding.ActivityMyEventBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MyEvent : AppCompatActivity() {
    private lateinit var binding: ActivityMyEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()
        setViewPager()
    }

    private fun setViewPager() {
        val viewPager: ViewPager2 = binding.viewPager
        val eventPagerAdapter = EventPagerAdapter(this)

        viewPager.adapter = eventPagerAdapter

        val buttonSemua = binding.buttonSemua
        val buttonOngoing = binding.buttonOnGoing
        val buttonEnd = binding.buttonSelesai

        buttonSemua.setOnClickListener {
            viewPager.currentItem = 0
        }

        buttonOngoing.setOnClickListener {
            viewPager.currentItem = 1
        }

        buttonEnd.setOnClickListener {
            viewPager.currentItem = 2
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateButton(position)
            }
        })

        updateButton(viewPager.currentItem)
    }

    private fun updateButton(selectedPosition: Int) {
        val unselectedBackground = ContextCompat.getDrawable(this, R.drawable.btn_unselected)
        val selectedBackground = ContextCompat.getDrawable(this, R.drawable.btn_selected)
        val unselectedText = ContextCompat.getColor(this, R.color.disable)
        val selectedText = ContextCompat.getColor(this, R.color.biru_toska)

        binding.buttonSemua.background = unselectedBackground
        binding.buttonSemua.setTextColor(unselectedText)
        binding.buttonOnGoing.background = unselectedBackground
        binding.buttonOnGoing.setTextColor(unselectedText)
        binding.buttonSelesai.background = unselectedBackground
        binding.buttonSelesai.setTextColor(unselectedText)

        when (selectedPosition) {
            0 -> {
                binding.buttonSemua.background = selectedBackground
                binding.buttonSemua.setTextColor(selectedText)
            }
            1 -> {
                binding.buttonOnGoing.background = selectedBackground
                binding.buttonOnGoing.setTextColor(selectedText)
            }
            2 -> {
                binding.buttonSelesai.background = selectedBackground
                binding.buttonSelesai.setTextColor(selectedText)
            }
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
        val customActionBar = LayoutInflater.from(this).inflate(R.layout.actionbar_event, null)
        val actionBarParams = ActionBar.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT
        )
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(customActionBar, actionBarParams)
    }
}