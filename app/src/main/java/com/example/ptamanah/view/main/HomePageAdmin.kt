package com.example.ptamanah.view.main

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.ptamanah.R
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.preference.dataStore
import com.example.ptamanah.data.repository.AuthRepo
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityHomePageAdminBinding
import com.example.ptamanah.view.admin.events.ListEventsAdmin
import com.example.ptamanah.view.admin.events.ListEventsAdmin.Companion.TOKEN
import com.example.ptamanah.view.admin.manageuser.ManageUserAdminActivity
import com.example.ptamanah.viewModel.factory.AuthViewModelFactory
import com.example.ptamanah.viewModel.admin.mainadmin.HomePageAdminViewModel

class HomePageAdmin : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageAdminBinding
    private var token: String = ""
    private val userPreference: UserPreference by lazy { UserPreference(this.dataStore) }
    private val viewModel: HomePageAdminViewModel by viewModels {
        AuthViewModelFactory(AuthRepo(ApiConfig.getApiService(), userPreference))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        viewModel.getSession().observe(this) { user ->
            token = user.toString()
            binding.eventsButton.setOnClickListener {
                val intent = Intent(this, ListEventsAdmin::class.java)
                intent.putExtra(TOKEN, token)
                startActivity(intent)
            }
            binding.ManageButton.setOnClickListener {
                val intent = Intent(this, ManageUserAdminActivity::class.java)
                intent.putExtra(TOKEN, token)
                startActivity(intent)
            }
        }
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    R.color.biru_toska
                )
            )
        )

        val customActionBar = LayoutInflater.from(this).inflate(R.layout.actionbar_main, null)
        val actionBarParams = ActionBar.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT
        )
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(customActionBar, actionBarParams)
        val imageViewTitle = customActionBar.findViewById<ImageView>(R.id.imageTitle)
        imageViewTitle.setImageResource(R.drawable.logo_putih)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_login -> {
                AlertDialog.Builder(this).apply {
                    setMessage("Apakah anda yakin ingin keluar?")
                    setPositiveButton("Ok") { _, _ ->
                        viewModel.logout()
                        val intent = Intent(context, HomePageCashier::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    create()
                    show()
                }
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}