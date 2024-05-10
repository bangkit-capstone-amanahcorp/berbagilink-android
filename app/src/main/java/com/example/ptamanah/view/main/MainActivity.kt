package com.example.ptamanah.view.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ptamanah.R
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.preference.dataStore
import com.example.ptamanah.data.repository.AuthRepo
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityMainBinding
import com.example.ptamanah.view.login.LoginActivity
import com.example.ptamanah.view.myEvent.MyEvent
import com.example.ptamanah.view.myEvent.MyEvent.Companion.TOKEN

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var token : String = ""
    private val userPreference: UserPreference by lazy { UserPreference(this.dataStore) }
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(AuthRepo(ApiConfig.getApiService(), userPreference))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getSession().observe(this) { user ->
            if (user.isNullOrEmpty()) {
                Log.d("isiUser", user.toString())
                binding.elevatedButton.setOnClickListener {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            } else {
                token = user
                Intent(this@MainActivity, MyEvent::class.java).apply {
                    putExtra(TOKEN, token)
                }.also {
                    startActivity(it)
                }
            }
        }

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_login -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}