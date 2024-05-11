package com.example.ptamanah.view.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

        ActionLogin()

    }

    private fun ActionLogin() {

        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.passwordEditTextLayout.error = null
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
