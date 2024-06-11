package com.example.ptamanah.view.admin.transaction

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ptamanah.R
import com.example.ptamanah.adapter.LoadingStateAdapter
import com.example.ptamanah.adapter.TransactionEventsAdapter
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.preference.dataStore
import com.example.ptamanah.data.repository.TransactionRepository
import com.example.ptamanah.data.response.DataItemTransaction
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityDaftarTransaksiBinding
import com.example.ptamanah.viewModel.admin.transaction.TransactionEventsViewModel
import com.example.ptamanah.viewModel.factory.TransactionViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DaftarTransaksiActivity : AppCompatActivity(), FilterStatusTransaksi.OnFilterSelectedListener {
    private lateinit var binding: ActivityDaftarTransaksiBinding
    private var username: String? = null

    private val transactionEventsViewModel: TransactionEventsViewModel by viewModels {
        TransactionViewModelFactory(getTransactionRepo())
    }
    private val transactionAdapter = TransactionEventsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDaftarTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        binding.btnFiltertransaksi.setOnClickListener {
            val bottomSheet = FilterStatusTransaksi()
            bottomSheet.setOnFilterSelectedListener(this)
            bottomSheet.show(supportFragmentManager, FilterStatusTransaksi.TAG)
        }

        setupRecyclerView()
        setupSearchView()

        val token = intent.getStringExtra(TOKEN).toString()
        val eventId = intent.getStringExtra(ID_EVENT).toString()
        val namaEvent = intent.getStringExtra(NAMA_EVENT).toString()

        // Retrieve username
        lifecycleScope.launch {
            getTransactionRepo().getUsername().collect { retrievedUsername ->
                username = retrievedUsername
                transactionAdapter.setUsername(username)
            }
        }

        transactionAdapter.setOnDetailClickCallBack(object : TransactionEventsAdapter.OnDetailClickCallBack {
            override fun onDetailClicked(user: DataItemTransaction) {
                Toast.makeText(this@DaftarTransaksiActivity, "Detail Transaksi: ${user.nama}", Toast.LENGTH_SHORT).show()
            }
        })

        Log.d("DaftarTransaksiActivity", "Received token: $token")
        Log.d("DaftarTransaksiActivity", "Received eventId: $eventId")
        Log.d("DaftarTransaksiActivity", "Received namaEvent: $namaEvent")
        Log.d("DaftarTransaksiActivity", "Received namaEvent: $username")

        transactionAdapter.setNamaEvent(namaEvent)

        lifecycleScope.launch {
            transactionEventsViewModel.getTransaksi(token, eventId).collectLatest { pagingData ->
                transactionAdapter.submitData(pagingData)
            }
        }

        observeLoadState()
    }

    private fun setupRecyclerView() {
        binding.rvReview.layoutManager = LinearLayoutManager(this)
        binding.rvReview.adapter = transactionAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                transactionAdapter.retry()
            }
        )
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                transactionEventsViewModel.searchTransaksi(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                transactionEventsViewModel.searchTransaksi(newText)
                return false
            }
        })
    }

    override fun onFilterSelected(status: String) {
        transactionEventsViewModel.filterTransaksi(status)
        binding.btnFiltertransaksi.text = if (status.isEmpty()) "Semua Status" else status
    }

    private fun observeLoadState() {
        lifecycleScope.launch {
            transactionAdapter.loadStateFlow.collectLatest {
                val isLoading = it.refresh is LoadState.Loading
                showLoading(isLoading)
                val isListEmpty = it.refresh is LoadState.NotLoading && transactionAdapter.itemCount == 0
                binding.NotfoundTv.visibility = if (isListEmpty) View.VISIBLE else View.GONE
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun getTransactionRepo(): TransactionRepository {
        val apiService = ApiConfig.getApiService()
        return TransactionRepository(apiService, UserPreference(this.dataStore))
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        finish()
        super.onBackPressed()
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
        val customActionBar = LayoutInflater.from(this).inflate(R.layout.actionbar_transaction_events, null)
        val actionBarParams = ActionBar.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT,
            ActionBar.LayoutParams.MATCH_PARENT
        )
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setCustomView(customActionBar, actionBarParams)
    }

    companion object {
        const val ID_EVENT = "id"
        const val TOKEN = "token"
        const val NAMA_EVENT = "nama_event"
    }
}
