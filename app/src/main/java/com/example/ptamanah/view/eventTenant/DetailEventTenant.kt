package com.example.ptamanah.view.eventTenant

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ptamanah.R
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.preference.dataStore
import com.example.ptamanah.data.repository.AuthRepo
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityDetailEventTenantBinding
import com.example.ptamanah.view.login.LoginActivity
import com.example.ptamanah.view.main.MainActivity
import com.example.ptamanah.viewModel.event.EventTenantViewModel
import com.example.ptamanah.viewModel.factory.AuthViewModelFactory

class DetailEventTenant : AppCompatActivity() {

    private lateinit var binding: ActivityDetailEventTenantBinding
    private val userPreference: UserPreference by lazy { UserPreference(this.dataStore) }
    private val viewModel: EventTenantViewModel by viewModels {
        AuthViewModelFactory(AuthRepo(ApiConfig.getApiService(), userPreference))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventTenantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this, R.color.biru_toska)))

        val customActionBar = LayoutInflater.from(this).inflate(R.layout.actionbar_main, null)
        val actionBarParams = ActionBar.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT
        )
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(customActionBar, actionBarParams)
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
                            val intent = Intent(context, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
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