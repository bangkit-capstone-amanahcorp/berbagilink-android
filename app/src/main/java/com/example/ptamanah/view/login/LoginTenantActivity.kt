package com.example.ptamanah.view.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ptamanah.R
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.preference.dataStore
import com.example.ptamanah.data.repository.AuthRepo
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityLoginTenantBinding
import com.example.ptamanah.view.eventTenant.ListEventTenant
import com.example.ptamanah.view.eventTenant.ListEventTenant.Companion.EMAIL
import com.example.ptamanah.viewModel.factory.AuthViewModelFactory
import com.example.ptamanah.viewModel.login.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class LoginTenantActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginTenantBinding
    private val userPreference: UserPreference by lazy { UserPreference(this.dataStore) }
    private val loginViewModel: LoginViewModel by viewModels {
        AuthViewModelFactory(AuthRepo(ApiConfig.getApiService(), userPreference))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginTenantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginTenant()

        binding.backLoginTenant.setOnClickListener {
            finish()
        }
    }

    private fun loginTenant() {
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.emailEditTextLayout.error = null
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.btnCheckEmail.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

            if (email.isEmpty()) {
                binding.emailEditTextLayout.error = "Silahkan Isi Emailnya Dulu"
            } else if (!email.matches(emailPattern.toRegex())) {
                binding.emailEditTextLayout.error = "Email Harus Valid"
            } else{
                showLoading(true)
                lifecycleScope.launch {
                    loginViewModel.checkEmail(email).collect{ result ->
                        result.onSuccess {
                            Intent(this@LoginTenantActivity, ListEventTenant::class.java).apply {
                                putExtra(EMAIL, email)
                            }.also {
                                startActivity(it)
                            }
                            showLoading(false)
                        }
                        result.onFailure {
                            Snackbar.make(this@LoginTenantActivity, binding.root, "Silahkan periksa internet anda terlebih dahulu", Snackbar.LENGTH_LONG).show()
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.backGroundOverlay.visibility = if (state) View.VISIBLE else View.GONE
        binding.cardViewLoading.visibility = if (state) View.VISIBLE else View.GONE
        setButtonsEnabled(!state)
    }

    private fun setButtonsEnabled(enabled: Boolean) {
        binding.emailEditText.isEnabled = enabled
        binding.btnCheckEmail.isEnabled = enabled
        binding.backLoginTenant.isEnabled = enabled
    }
}