package com.example.ptamanah.view.admin.detailEvent

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.ptamanah.R
import com.example.ptamanah.data.repository.EventRepository
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityDetailEventBinding
import com.example.ptamanah.view.admin.logcheck.EventAdmin
import com.example.ptamanah.view.admin.logcheck.EventAdminFragment
import com.example.ptamanah.view.camera.CameraActivity
import com.example.ptamanah.view.myEventCashier.MyEventFragment
import com.example.ptamanah.viewModel.admin.detailEvents.DetailEventsViewModel
import com.example.ptamanah.viewModel.factory.EventViewModelFactory
import kotlinx.coroutines.launch

class DetailEventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailEventBinding
    private var token: String? =""
    private var id: String? = ""
    private val eventViewModel: DetailEventsViewModel by viewModels {
        EventViewModelFactory(EventRepository(ApiConfig.getApiService()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        token = intent.getStringExtra(TOKENDETAIL)
        id = intent.getStringExtra(ID_EVENT)

        getDetailEvent()

    }

    private fun getDetailEvent() {
        showLoading(true)
        lifecycleScope.launch {
            eventViewModel.getDetailEvent(token.toString(), id.toString()).collect { result ->
                result.onSuccess { response ->
                    response.data?.let {
                        Glide.with(this@DetailEventActivity)
                            .load("https://img.freepik.com/free-photo/painting-mountain-lake-with-mountain-background_188544-9126.jpg")
                            .into(binding.LogoEvent)
                        Glide.with(this@DetailEventActivity)
                            .load("https://static.vecteezy.com/system/resources/thumbnails/028/626/672/small_2x/hd-image-ai-generative-free-photo.jpeg")
                            .into(binding.LogoOrganizer)
                        if (response.data.saleStatus == "active") {
                            binding.tvStatus.text = "On-Going"
                            binding.scnaBtn.setOnClickListener {
                                Intent(this@DetailEventActivity, CameraActivity::class.java).apply {
                                    putExtra(CameraActivity.ID_EVENT, id)
                                    putExtra(CameraActivity.TOKEN, token)
                                }.also {
                                    startActivity(it)
                                }
                            }
                        } else {
                            binding.tvStatus.text = "Selesai"
                            binding.tvStatus.setTextColor(
                                ContextCompat.getColor(
                                    this@DetailEventActivity,
                                    R.color.disable
                                )
                            )
                            binding.statusIV.setImageResource(R.drawable.status_border_end)
                            binding.scnaBtn.backgroundTintList = ContextCompat.getColorStateList(this@DetailEventActivity, R.color.disable)
                            binding.scnaBtn.setTextColor(ContextCompat.getColor(this@DetailEventActivity, R.color.white))
                            binding.scnaBtn.isEnabled = false
                        }
                        binding.apply {
                            NamaEvent.text = it.namaEvent
                            NamaOrganizer.text = it.namaOrganizer
                            TextJenisEvent.text = it.type
                            TextTanggal.text = it.tanggalStart + " - " + it.tanggalEnd
                            TextWaktu.text = it.waktuStart + " - " + it.waktuEnd
                            TextLokasi.text = it.namaTempat
                            TextAlamat.text = it.alamat
                            TextDeskripsi.text = it.description

                            daftarBtn.setOnClickListener {
                                Intent(this@DetailEventActivity, EventAdmin::class.java).apply {
                                    putExtra(EventAdminFragment.EVENT_ID, id)
                                    putExtra(MyEventFragment.TOKEN, token)
                                }.also {
                                    startActivity(it)
                                }
                            }
                        }
                    }
                    showLoading(false)
                }
                result.onFailure {
                    showLoading(false)
                    Toast.makeText(
                        this@DetailEventActivity,
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    androidx.cardview.R.color.cardview_light_background
                )
            )
        )
        supportActionBar?.title = "Detail Events"
    }

    private fun showLoading(state: Boolean) {
        binding.pbLoading.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val ID_EVENT = "id"
        const val TOKENDETAIL = "token"
    }
}