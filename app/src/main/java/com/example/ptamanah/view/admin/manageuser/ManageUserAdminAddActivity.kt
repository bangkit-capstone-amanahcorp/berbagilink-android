package com.example.ptamanah.view.admin.manageuser

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.ptamanah.R
import com.example.ptamanah.data.repository.ManagementUserRepository
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityManageUserAdminAddBinding
import com.example.ptamanah.view.admin.logcheck.FilteringStatus
import com.example.ptamanah.view.admin.manageuser.editUser.fragmentEdit.RoleFragment
import com.example.ptamanah.view.eventTenant.BottomLogin
import com.example.ptamanah.viewModel.factory.ManagementUserViewModelFactory
import com.example.ptamanah.viewModel.managementuser.ManagementUserAddViewModel
import kotlinx.coroutines.launch

class ManageUserAdminAddActivity : AppCompatActivity(),RoleFragment.RoleSelectedListener{
    private lateinit var binding: ActivityManageUserAdminAddBinding
    private lateinit var token: String
    private var roleFragment: RoleFragment? = null
    private val managementUserAddViewModel:ManagementUserAddViewModel by viewModels {
        ManagementUserViewModelFactory(getManagementUserRepo())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageUserAdminAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = intent.getStringExtra(TOKEN) ?: ""

        setupView()
        setupAction()


        binding.btnRole.setOnClickListener{
            roleFragment = RoleFragment().apply {
                setRoleSelected(this@ManageUserAdminAddActivity)
                arguments = Bundle().apply {
                    putString(RoleFragment.CURRENT_ROLE, getCurrentRole())
                }
            }
            roleFragment?.show(supportFragmentManager, RoleFragment.TAG)
        }
        binding.edRegisterConfirmPassword.passwordField = binding.edRegisterPassword
    }
    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun setupAction() {
        binding.btnsimpan.setOnClickListener {
            val name = binding.edRegisterName.text.toString().trim()
            val email = binding.edRegisterEmail.text.toString().trim()
            val password = binding.edRegisterPassword.text.toString().trim()
            val passwordConfirmation = binding.edRegisterConfirmPassword.text.toString().trim()
            val role = roleFragment?.getSelectedStatus()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || passwordConfirmation.isEmpty()) {
                Toast.makeText(this, "Harap isi semua data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (binding.edRegisterPassword.error != null || binding.edRegisterConfirmPassword.error != null) {
                Toast.makeText(this, "Periksa kembali password yang dimasukkan", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                lifecycleScope.launch {
                    Log.d("aaaaa", "Mengirim data: $name, $email, $role")
                    managementUserAddViewModel.addManagementUser(
                        token,
                        name,
                        email,
                        password,
                        passwordConfirmation,
                        role.toString().trim()
                    ).collect { result ->
                        result.onSuccess {
                            Toast.makeText(
                                this@ManageUserAdminAddActivity,
                                "Data berhasil Ditambah",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(
                                this@ManageUserAdminAddActivity,
                                ManageUserAdminActivity::class.java
                            ).apply {
                                putExtra(ManageUserAdminActivity.TOKEN, token)
                            }
                            Log.d("bbbbb", "Data berhasil terkirim: $name, $email, $password, $role")
                            startActivity(intent)
                            finish()
                        }
                        result.onFailure {
                            Toast.makeText(
                                this@ManageUserAdminAddActivity,
                                "Data gagal ditambah",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.e("ManageUserAdminAdd", "Gagal mengirim data: ${it.message}")
                        }
                    }
                }
            }
        }
    }


    private fun getManagementUserRepo(): ManagementUserRepository {
        val apiService = ApiConfig.getApiService()
        return ManagementUserRepository(apiService)
    }
    override fun RoleSelected(status: String) {
        if (status == "cashier") {
            binding.btnRole.text = "Kasir"
        } else if (status == "user_editor") {
            binding.btnRole.text = "Tenant"
        }else if (status == "user_administrator") {
            binding.btnRole.text = "Admin"
        }
    }
    private fun getCurrentRole(): String {
        return when (binding.btnRole.text.toString()) {
            "Kasir" -> "cashier"
            "Tenant" -> "user_editor"
            "Admin" -> "user_administrator"
            else -> ""
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
    }
    companion object {
        const val TOKEN = "token"
    }
}