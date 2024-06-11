package com.example.ptamanah.view.admin.statistik

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.ptamanah.R
import com.example.ptamanah.data.repository.EventRepository
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityStatisticBinding
import com.example.ptamanah.viewModel.admin.statistic.StatisticEventsViewModel
import com.example.ptamanah.viewModel.factory.EventViewModelFactory
import kotlinx.coroutines.launch

class StatisticActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatisticBinding
    private var token: String? = ""
    private var idEvent: String? = ""
    private val eventViewModel: StatisticEventsViewModel by viewModels{
        EventViewModelFactory(EventRepository(ApiConfig.getApiService()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatisticBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra(TOKEN_STATISTIC)
        idEvent = intent.getStringExtra(ID_EVENT)

        getStatistic()
        setupActionBar()
    }

    private fun getStatistic() {
        showLoading(true)
        lifecycleScope.launch {
            eventViewModel.getStatisticEvents(token.toString(), idEvent.toString()).collect { result ->
                result.onSuccess {
                    binding.apply {
                        ivCheck.text = it.ticket?.sudahCheckIn.toString()
                        ivBelumCheck.text = it.ticket?.belumCheckIn.toString()
                        ivGagalCheck.text = it.ticket?.failedCheckIn.toString()
                        ivTotalTiketBerbayar.text = it.ticket?.totalTiket.toString()
                        ivCheck2.text = it.manualTicket?.sudahCheckIn.toString()
                        ivBelumCheck2.text = it.manualTicket?.belumCheckIn.toString()
                        ivGagalCheck2.text = it.manualTicket?.failedCheckIn.toString()
                        ivTotalTiketManual.text = it.manualTicket?.totalTiket.toString()
                    }
                    showLoading(false)
                }.onFailure {
                    showLoading(false)
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
        supportActionBar?.title = "Lihat Statistic"
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

    companion object {
        const val ID_EVENT = "id_event"
        const val TOKEN_STATISTIC = "token"
    }
}