package com.example.ptamanah.view.admin.detailEvent

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ptamanah.R
import com.example.ptamanah.adapter.TiketDetailEventAdapter
import com.example.ptamanah.data.repository.EventRepository
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityDetailEventBinding
import com.example.ptamanah.view.admin.logcheck.EventAdmin
import com.example.ptamanah.view.admin.logcheck.EventAdminFragment
import com.example.ptamanah.view.admin.statistik.StatisticActivity
import com.example.ptamanah.view.admin.transaction.DaftarTransaksiActivity
import com.example.ptamanah.view.camera.CameraActivity
import com.example.ptamanah.view.cashier.MyEventFragment
import com.example.ptamanah.viewModel.admin.detailEvents.DetailEventsViewModel
import com.example.ptamanah.viewModel.factory.EventViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class DetailEventActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager
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
        showTiket()

        binding.swipeRefresh.setOnRefreshListener {
            getDetailEvent()
            showTiket()
            binding.swipeRefresh.isRefreshing = false
        }

        binding.scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY == 0) {
                binding.swipeRefresh.isEnabled = true
            } else {
                binding.swipeRefresh.isEnabled = false
            }
        }
    }

    private fun getDetailEvent() {
        showLoading(true)
        lifecycleScope.launch {
            eventViewModel.getDetailEvent(token.toString(), id.toString()).collect { result ->
                result.onSuccess { response ->
                    response.data?.let {
                        val namaEvent = it.namaEvent
                        Glide.with(this@DetailEventActivity)
                            .load(it.imageCoverUrlFull)
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

                            lihatStat.setOnClickListener {
                                Intent(this@DetailEventActivity, StatisticActivity::class.java).apply {
                                    putExtra(StatisticActivity.ID_EVENT, id)
                                    putExtra(StatisticActivity.TOKEN_STATISTIC, token)
                                }.also {
                                    startActivity(it)
                                }
                            }
                            daftarTrans.setOnClickListener {
                                Intent(this@DetailEventActivity, DaftarTransaksiActivity::class.java).apply {
                                    putExtra(DaftarTransaksiActivity.ID_EVENT, id)
                                    putExtra(DaftarTransaksiActivity.TOKEN, token)
                                    putExtra(DaftarTransaksiActivity.NAMA_EVENT, namaEvent)
                                }.also {
                                    startActivity(it)
                                }
                            }

                            var isExpand = false
                            btnExpand.setOnClickListener {
                                isExpand = !isExpand
                                if (isExpand) {
                                    btnExpand.background = ContextCompat.getDrawable(this@DetailEventActivity, R.drawable.ic_arrow_down)
                                    JenisEvent.visibility = View.GONE
                                    LogoJenisEvent.visibility = View.GONE
                                    TextJenisEvent.visibility = View.GONE
                                    line2.visibility = View.GONE
                                    TanggalAndWaktu.visibility = View.GONE
                                    LogoTanggal.visibility = View.GONE
                                    TextTanggal.visibility = View.GONE
                                    LogoWaktu.visibility = View.GONE
                                    TextWaktu.visibility = View.GONE
                                    line3.visibility = View.GONE
                                    Lokasi.visibility = View.GONE
                                    LogoLokasi.visibility = View.GONE
                                    TextLokasi.visibility = View.GONE
                                    line4.visibility = View.GONE
                                    Alamat.visibility = View.GONE
                                    LogoAlamat.visibility = View.GONE
                                    TextAlamat.visibility = View.GONE
                                    line5.visibility = View.GONE
                                    Deskripsi.visibility = View.GONE
                                    TextDeskripsi.visibility = View.GONE
                                } else {
                                    btnExpand.background = ContextCompat.getDrawable(this@DetailEventActivity, R.drawable.ic_arrow_up)
                                    JenisEvent.visibility = View.VISIBLE
                                    LogoJenisEvent.visibility = View.VISIBLE
                                    TextJenisEvent.visibility = View.VISIBLE
                                    line2.visibility = View.VISIBLE
                                    TanggalAndWaktu.visibility = View.VISIBLE
                                    LogoTanggal.visibility = View.VISIBLE
                                    TextTanggal.visibility = View.VISIBLE
                                    LogoWaktu.visibility = View.VISIBLE
                                    TextWaktu.visibility = View.VISIBLE
                                    line3.visibility = View.VISIBLE
                                    Lokasi.visibility = View.VISIBLE
                                    LogoLokasi.visibility = View.VISIBLE
                                    TextLokasi.visibility = View.VISIBLE
                                    line4.visibility = View.VISIBLE
                                    Alamat.visibility = View.VISIBLE
                                    LogoAlamat.visibility = View.VISIBLE
                                    TextAlamat.visibility = View.VISIBLE
                                    line5.visibility = View.VISIBLE
                                    Deskripsi.visibility = View.VISIBLE
                                    TextDeskripsi.visibility = View.VISIBLE
                                }
                            }

                            var isExpandTiket = false
                            btnExpandTiket.setOnClickListener {
                                isExpandTiket = !isExpandTiket
                                if (isExpandTiket) {
                                    btnExpandTiket.background = ContextCompat.getDrawable(this@DetailEventActivity, R.drawable.ic_arrow_down)
                                    rvTiketKegiatan.visibility = View.GONE
                                } else {
                                    btnExpandTiket.background = ContextCompat.getDrawable(this@DetailEventActivity, R.drawable.ic_arrow_up)
                                    rvTiketKegiatan.visibility = View.VISIBLE
                                }
                            }
                        }
                    }
                    showLoading(false)
                }
                result.onFailure {
                    showLoading(false)
                    Snackbar.make(this@DetailEventActivity, binding.root, "Silahkan periksa internet anda terlebih dahulu", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showTiket() {
        linearLayoutManager = LinearLayoutManager(this)
        binding.rvTiketKegiatan.layoutManager = linearLayoutManager
        val tikeAdapter = TiketDetailEventAdapter()
        binding.rvTiketKegiatan.adapter = tikeAdapter

        lifecycleScope.launch {
            eventViewModel.getTiket(token.toString(), id.toString()).collect { result ->
                result.onSuccess { response ->
                    tikeAdapter.submitList(response.data?.tickets)

                    if (response.data?.tickets.isNullOrEmpty()) {
                        binding.tvNotFound.visibility = View.VISIBLE
                    }
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