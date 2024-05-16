package com.example.ptamanah.view.eventTenant

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ptamanah.R
import com.example.ptamanah.adapter.EventListTenantAdapter
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.preference.dataStore
import com.example.ptamanah.data.repository.AuthRepo
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityListEventTenantBinding
import com.example.ptamanah.viewModel.factory.AuthViewModelFactory
import com.example.ptamanah.viewModel.login.LoginViewModel
import kotlinx.coroutines.launch

class ListEventTenant : AppCompatActivity() {
    private lateinit var binding: ActivityListEventTenantBinding
    private var email: String = ""
    private val userPreference: UserPreference by lazy { UserPreference(this.dataStore) }
    private val loginViewModel: LoginViewModel by viewModels {
        AuthViewModelFactory(AuthRepo(ApiConfig.getApiService(), userPreference))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListEventTenantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        email = intent.getStringExtra(EMAIL).toString()
        getAllEvent()
    }

    private fun getAllEvent() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvEvent.layoutManager = layoutManager
        val adapter = EventListTenantAdapter()
        binding.rvEvent.adapter = adapter

        lifecycleScope.launch {
            loginViewModel.checkEmail(email).collect{ result ->
                result.onSuccess { response ->
                    binding.apply {
                        if (response.data.isNullOrEmpty()) {
                            binding.tvStatus.visibility = View.VISIBLE
                        } else {
                            adapter.submitList(response.data)
                        }
                        loginViewModel.saveEmail(email)
                    }



                    showLoading(false)
                }
                result.onFailure {
                    showLoading(false)
                    Toast.makeText(
                        this@ListEventTenant,
                        "Silahkan periksa internet anda terlebih dahulu",
                        Toast.LENGTH_SHORT
                    ).show()
                }
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
        val customActionBar = LayoutInflater.from(this).inflate(R.layout.actionbar_list_event_tenant, null)
        val actionBarParams = ActionBar.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT
        )
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(customActionBar, actionBarParams)

    }


    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val EMAIL = "email"
    }
}