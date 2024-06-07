package com.example.ptamanah.view.admin.manageuser.editUser

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.ptamanah.R
import com.example.ptamanah.adapter.EditUserPagerAdapter
import com.example.ptamanah.adapter.EventPagerAdminLogAdapter
import com.example.ptamanah.databinding.ActivityEditUserBinding
import com.example.ptamanah.view.admin.manageuser.editUser.fragmentEdit.GeneralFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class EditUserActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditUserBinding
    private lateinit var token: String
    private lateinit var email: String
    private var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        token = intent.getStringExtra(GeneralFragment.TOKEN) ?: ""
        id = intent.getIntExtra(GeneralFragment.ID, 0)

        Log.d("idnyaLe", id.toString())
        setupViewPager()

    }

    private fun setupViewPager() {
        val viewPager: ViewPager2 = binding.pagerAdmin
        val EditUserAdapter = EditUserPagerAdapter(this, token, id)

        viewPager.adapter = EditUserAdapter
        val tabLayout: TabLayout = binding.tabLayout
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "General"
                1 -> tab.text = "Password"
            }
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    androidx.cardview.R.color.cardview_light_background
                )
            )
        )
        supportActionBar?.title = "Edit User"
    }
}