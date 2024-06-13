package com.example.ptamanah.view.main

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.ptamanah.R
import com.example.ptamanah.R.id.imageTitle
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.preference.dataStore
import com.example.ptamanah.data.repository.AuthRepo
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityMainBinding
import com.example.ptamanah.view.login.LoginActivity
import com.example.ptamanah.view.cashier.MyEvent
import com.example.ptamanah.view.cashier.MyEventFragment.Companion.TOKEN
import com.example.ptamanah.viewModel.factory.AuthViewModelFactory
import com.example.ptamanah.viewModel.main.MainViewModel

class HomePageCashier : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var token : String = ""
    private val userPreference: UserPreference by lazy { UserPreference(this.dataStore) }
    private val viewModel: MainViewModel by viewModels {
        AuthViewModelFactory(AuthRepo(ApiConfig.getApiService(), userPreference))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        viewModel.getSession().observe(this) { user ->
            if (user.isNullOrEmpty()) {
                binding.elevatedButton.setOnClickListener {
                    startActivity(Intent(this, LoginActivity::class.java))
                }
            } else {
                token = user
                binding.elevatedButton.text = "Lihat Event"
                binding.elevatedButton.setOnClickListener {
                    Intent(this@HomePageCashier, MyEvent::class.java).apply {
                        putExtra(TOKEN, token)
                    }.also {
                        startActivity(it)
                    }
                }
            }
        }

        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.carousle))
        imageList.add(SlideModel(R.drawable.carousle))
        imageList.add(SlideModel(R.drawable.carousle))

        binding.imageSlider.setImageList(imageList, ScaleTypes.FIT)

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
        val imageViewTitle = customActionBar.findViewById<ImageView>(imageTitle)
        imageViewTitle.setImageResource(R.drawable.logo_putih)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_login -> {
                if (token.isNullOrEmpty()) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    AlertDialog.Builder(this).apply {
                        setMessage("Apakah anda yakin ingin keluar?")
                        setPositiveButton("Ok") { _, _ ->
                            viewModel.logout()
                            val intent = Intent(context, HomePageCashier::class.java)
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
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}