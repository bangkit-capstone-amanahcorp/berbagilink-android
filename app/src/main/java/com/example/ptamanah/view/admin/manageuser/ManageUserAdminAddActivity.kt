package com.example.ptamanah.view.admin.manageuser

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ptamanah.R
import com.example.ptamanah.data.repository.ManagementUserRepository
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityManageUserAdminAddBinding
import com.example.ptamanah.view.admin.logcheck.FilteringStatus
import com.example.ptamanah.view.eventTenant.BottomLogin
import com.example.ptamanah.viewModel.factory.ManagementUserViewModelFactory
import com.example.ptamanah.viewModel.managementuser.ManagementUserAddViewModel

class ManageUserAdminAddActivity : AppCompatActivity(),FilterRole.OnFilterSelectedListener{
    private lateinit var binding: ActivityManageUserAdminAddBinding
    private val managementUserAddViewModel:ManagementUserAddViewModel by viewModels {
        ManagementUserViewModelFactory(getManagementUserRepo())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManageUserAdminAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupAction()

        binding.btnRole.setOnClickListener{
            val bottomShet = FilterRole()
            bottomShet.setOnFilterSelectedListener(this)

        }

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
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            val passwordConfirmation = binding.edRegisterConfirmPassword.text.toString()


            viewModel.register(name, email, password)

            viewModel.signupResult.observe(this) { result ->
                showLoading(result is ResultState.Loading)

                when (result) {
                    is ResultState.Success -> {
                        showLoading(false)
                        result.data.let { data ->
                            data.message?.let { message ->
                                AlertDialog.Builder(this).apply {
                                    setTitle("Yeah!")
                                    setMessage("Akun dengan $email sudah jadi nih. Yuk, login dan belajar coding.")
                                    setPositiveButton("Lanjut") { _, _ ->
                                        finish()
                                        startActivity(
                                            Intent(
                                                this@SignupActivity,
                                                LoginActivity::class.java
                                            )
                                        )
                                    }
                                    create()
                                    show()
                                }
                            }
                        }
                    }

                    is ResultState.Error -> {
                        showLoading(false)
                        AlertDialog.Builder(this).apply {
                            setTitle("Oops...")
                            setMessage(result.error)
                            setPositiveButton("Coba Lagi") { _, _ ->
                            }
                            create()
                            show()
                        }
                    }

                    is ResultState.Loading -> showLoading(true)
                }
            }
        }
    }

    private fun getManagementUserRepo(): ManagementUserRepository {
        val apiService = ApiConfig.getApiService()
        return ManagementUserRepository(apiService)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) android.view.View.VISIBLE else android.view.View.GONE
    }
}