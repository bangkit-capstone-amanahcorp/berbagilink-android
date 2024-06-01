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
import com.example.ptamanah.view.main.HomePageCashier
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.preference.dataStore
import com.example.ptamanah.data.repository.AuthRepo
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityLoginBinding
import com.example.ptamanah.view.main.HomePageAdmin
import com.example.ptamanah.viewModel.factory.AuthViewModelFactory
import com.example.ptamanah.viewModel.login.LoginViewModel
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val userPreference: UserPreference by lazy { UserPreference(this.dataStore) }
    private val loginViewModel: LoginViewModel by viewModels {
        AuthViewModelFactory(AuthRepo(ApiConfig.getApiService(), userPreference))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getSupportActionBar()?.hide()
        ActionLogin()
        loginTenant()
    }

    private fun ActionLogin() {
        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.emailEditTextLayout.error = null
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.passwordEditTextLayout.error = null
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

            if (email.isEmpty()) {
                binding.emailEditTextLayout.error =
                    "The field is required"
            } else if (!email.matches(emailPattern.toRegex())) {
                binding.emailEditTextLayout.error =
                    "The field is not a valid email address"
            } else if (password.isEmpty()) {
                binding.passwordEditTextLayout.error =
                    "The field is required"
            } else if (password.length < 5) {
                binding.passwordEditTextLayout.error =
                    "The field must be at least 5 characters long"
            } else {
                showLoading(true)
                lifecycleScope.launch {
                    loginViewModel.userLogin(email, password).collect { result ->
                        result.onSuccess { credensial ->
                            if (credensial.data?.user?.role == "user_super_administrator") {
                                credensial.data.accessToken?.let { token ->
                                    loginViewModel.saveAuthAdmin(token)
                                    val intent = Intent(this@LoginActivity, HomePageAdmin::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    showLoading(false)
                                    finish()
                                }

                            } else if (credensial.data?.user?.role == "cashier") {
                                credensial.data?.accessToken?.let { token ->
                                    loginViewModel.saveAuthToken(token)
                                    val intent =
                                        Intent(this@LoginActivity, HomePageCashier::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                    showLoading(false)
                                    finish()
                                }
                            }

                            showToast(credensial.info.toString())
                            showLoading(false)
                        }
                        result.onFailure {
                            showToast("Login gagal")
                            showLoading(false)
                        }
                    }
                }
            }
        }
    }

    private fun loginTenant() {
        binding.loginTenant.setOnClickListener {
            startActivity(Intent(this, LoginTenantActivity::class.java))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        binding.cardViewLoading.visibility = if (state) View.VISIBLE else View.GONE
        binding.backGroundOverlay.visibility = if (state) View.VISIBLE else View.GONE
        setButtonsEnabled(!state)
    }

    private fun setButtonsEnabled(enabled: Boolean) {
        binding.emailEditText.isEnabled = enabled
        binding.passwordEditTextLayout.isEnabled = enabled
        binding.loginButton.isEnabled = enabled
        binding.loginTenant.isEnabled = enabled
    }
}
