package com.example.ptamanah.view.settings

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ExpandableListView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.ptamanah.R
import com.example.ptamanah.adapter.navigation.ExpandableListAdapter
import com.example.ptamanah.databinding.ActivityPageBinding
import com.google.android.material.appbar.MaterialToolbar

class PageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPageBinding

    private lateinit var expandableListView: ExpandableListView
    private lateinit var adapter: ExpandableListAdapter
    private lateinit var titleList: List<String>
    private lateinit var childList: HashMap<String, List<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupActionBar()

        expandableListView = binding.expandableListView

        setupExpandableList()

        binding.topAppBar.setNavigationOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        val navigationView = binding.navigationView
        val headerView = navigationView.getHeaderView(0)

        headerView.findViewById<ImageButton>(R.id.close).setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }


    private fun setupExpandableList() {
        val expandableListView: ExpandableListView = findViewById(R.id.expandableListView)
        val listData = HashMap<String, List<String>>()
        listData["Menu 1"] = listOf("Sub Menu 1", "Sub Menu 2")
        listData["Menu 2"] = listOf("Sub Menu 1", "Sub Menu 2")
        val listTitle = ArrayList(listData.keys)

        val adapter = ExpandableListAdapter(this, listTitle, listData)
        expandableListView.setAdapter(adapter)
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.topAppBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    androidx.cardview.R.color.cardview_light_background
                )
            )
        )
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_icon -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}