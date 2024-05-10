package com.example.ptamanah.view.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.ptamanah.view.main.MainActivity
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.preference.dataStore
import com.example.ptamanah.data.repository.AuthRepo
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val userPreference: UserPreference by lazy { UserPreference(this.dataStore) }
    private val loginViewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(AuthRepo(ApiConfig.getApiService(), userPreference))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getSupportActionBar()?.hide()

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

            if (email.isEmpty()) {
                binding.emailEditText.error = "Silahkan Isi Emailnya Dulu"
            } else if (!email.matches(emailPattern.toRegex())) {
                binding.emailEditText.error = "Email Harus Valid"
            } else if (password.isEmpty()) {
                binding.passwordEditTextLayout.error =
                    "Silahkan Isi Password Dulu"
            } else {

                showLoading(true)
                lifecycleScope.launch {
                    loginViewModel.userLogin(email, password).collect { result ->
                        result.onSuccess { credensial ->
                            Log.d("tokennya", credensial.data?.accessToken.toString())

                            credensial.data?.accessToken?.let { token ->
                                loginViewModel.saveAuthToken(token)
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                Log.d("tokennya", credensial.data.accessToken)
                                showLoading(false)
                                finish()
                            }
                        }
                        result.onFailure {
                            showToast("Login gagal")
                            showLoading(false)
                            Log.d("errorNi", result.toString())
                        }
                    }
                }
            }
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }

}
