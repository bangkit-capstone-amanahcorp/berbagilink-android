package com.example.ptamanah.view.eventTenant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ptamanah.R
import com.example.ptamanah.data.preference.UserPreference
import com.example.ptamanah.data.preference.dataStore
import com.example.ptamanah.data.repository.AuthRepo
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.FragmentBottomLoginBinding
import com.example.ptamanah.viewModel.factory.AuthViewModelFactory
import com.example.ptamanah.viewModel.login.LoginViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class BottomLogin : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomLoginBinding
    private var email: String = ""
    private var eventId: String = ""
    private val userPreference: UserPreference by lazy { UserPreference(requireContext().dataStore) }
    private val loginViewModel: LoginViewModel by viewModels {
        AuthViewModelFactory(AuthRepo(ApiConfig.getApiService(), userPreference))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        eventId = arguments?.getString(EVENT_ID).toString()

        loginViewModel.getEmail().observe(this@BottomLogin) {user ->
            email = user.toString()
        }

        Log.d("isiIdAwal", eventId)

        binding.checkEventButton.setOnClickListener {
            val password = binding.passwordEditText.text.toString().trim()

            if (password.isEmpty()) {
                binding.passwordEditTextLayout.error =
                    "The field is required"
            } else {
                lifecycleScope.launch {
                    loginViewModel.loginTenant(email, password, eventId).collect{ result ->
                        result.onSuccess { credential ->

                            if (credential.info == "Login berhasil") {
                                showToast("Berhasil masuk")
                                startActivity(Intent(context, DetailEventTenant::class.java))
                                loginViewModel.saveSessionTenant(credential.data?.token.toString())
                            } else {
                                showToast("Silahkan cek Email/No Handphone dan password anda.")
                                dismiss()
                            }

                        }
                        result.onFailure {
                            showToast("Silahkan periksa internet anda terlebih dahulu.")
                            dismiss()
                        }
                    }
                }
            }
        }
    }



    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val TAG = "AccountConfirmationBottomSheet"
        const val EVENT_ID = "event_id"
        const val USER = "user"

        fun newInstance(eventId: String): BottomLogin {
            val fragment = BottomLogin()
            val args = Bundle()
            args.putString(EVENT_ID, eventId)
            fragment.arguments = args
            return fragment
        }

    }
}