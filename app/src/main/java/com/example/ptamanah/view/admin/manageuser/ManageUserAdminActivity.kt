package com.example.ptamanah.view.admin.manageuser

import android.app.AlertDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ptamanah.R
import com.example.ptamanah.adapter.ManagementUserAdapter
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.preference.dataStore
import com.example.ptamanah.data.repository.ManagementUserRepository
import com.example.ptamanah.data.response.DataItemManagementUser
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityManageUserAdminBinding
import com.example.ptamanah.viewModel.factory.ManagementUserViewModelFactory
import com.example.ptamanah.viewModel.managementuser.ManagementUserViewModel
import kotlinx.coroutines.launch

class ManageUserAdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManageUserAdminBinding
    private val managementUserViewModel: ManagementUserViewModel by viewModels {
        ManagementUserViewModelFactory(getManagementUserRepo())
    }
    private val managementUserAdapter = ManagementUserAdapter()
    private val userPreference: UserPreference by lazy { UserPreference(this.dataStore) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageUserAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        setupRecyclerView()
        setupSearchView()

        val token = intent.getStringExtra(TOKEN).toString()

        managementUserAdapter.setOnDeleteClickCallBack(object : ManagementUserAdapter.OnDeleteClickCallBack {
            override fun onDeleteClicked(user: DataItemManagementUser) {
                showDeleteConfirmationDialog(token, user.id)
            }
        })

        managementUserAdapter.setOnEditClickCallBack(object : ManagementUserAdapter.OnEditClickCallBack {
            override fun onEditClicked(user: DataItemManagementUser) {
                Toast.makeText(this@ManageUserAdminActivity, "Edit clicked for ${user.name}", Toast.LENGTH_SHORT).show()
            }
        })

        lifecycleScope.launch {
            showLoading(true)
            managementUserViewModel.getAllUser(token)
            managementUserViewModel.filteredData.observe(this@ManageUserAdminActivity) { userList ->
                showLoading(false)
                managementUserAdapter.submitList(userList)
                handleSearchResults(userList)
            }
        }
    }

    private fun showDeleteConfirmationDialog(token: String, userId: Int) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setIcon(R.drawable.ic_baseline_error_outline_24)
            setTitle("Apakah Anda yakin?")
            setMessage("Anda tidak akan dapat mengembalikan data ini!")
            setPositiveButton("Ya, Hapus") { dialog, which ->
                lifecycleScope.launch {
                    showLoading(true)
                    deleteUser(token, userId)
                }
            }
            setNegativeButton("Cancel") { dialog, which ->
                dialog.dismiss()
            }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
    }

    private suspend fun deleteUser(token: String, userId: Int) {
        managementUserViewModel.deleteUser(token, userId).collect { result ->
            result.onSuccess {
                Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show()
                // Refresh
                showLoading(false)
                managementUserViewModel.getAllUser(token)
            }
            result.onFailure {
                Toast.makeText(this, "Failed to delete user", Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        }
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

    private fun setupRecyclerView() {
        binding.rvReview.layoutManager = LinearLayoutManager(this)
        binding.rvReview.adapter = managementUserAdapter
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                managementUserViewModel.searchUser(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                managementUserViewModel.searchUser(newText.orEmpty())
                return false
            }
        })
    }

    private fun handleSearchResults(userList: List<DataItemManagementUser>) {
        binding.NotfoundTv.visibility = if (userList.isEmpty()) View.VISIBLE else View.GONE
    }

    private fun getManagementUserRepo(): ManagementUserRepository {
        val apiService = ApiConfig.getApiService()
        return ManagementUserRepository(apiService)
    }

    private fun setupActionBar() {
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
            setDisplayHomeAsUpEnabled(true)
            setBackgroundDrawable(ColorDrawable(ContextCompat.getColor(this@ManageUserAdminActivity, androidx.cardview.R.color.cardview_light_background)))
            setDisplayShowCustomEnabled(true)
            setCustomView(LayoutInflater.from(this@ManageUserAdminActivity).inflate(R.layout.actionbar_manage_user, null), ActionBar.LayoutParams(
                ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.MATCH_PARENT
            ))
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    companion object {
        const val TOKEN = "token"
    }
}
