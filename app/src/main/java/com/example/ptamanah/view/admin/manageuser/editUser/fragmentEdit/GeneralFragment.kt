package com.example.ptamanah.view.admin.manageuser.editUser.fragmentEdit

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.R
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ptamanah.data.repository.ManagementUserRepository
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.FragmentGeneralBinding
import com.example.ptamanah.view.admin.logcheck.FilteringStatus
import com.example.ptamanah.view.admin.manageuser.ManageUserAdminActivity
import com.example.ptamanah.view.eventTenant.BottomLogin
import com.example.ptamanah.viewModel.factory.ManagementUserViewModelFactory
import com.example.ptamanah.viewModel.managementuser.ManagementUserViewModel
import kotlinx.coroutines.launch

class GeneralFragment : Fragment(), RoleFragment.RoleSelectedListener {

    private lateinit var binding: FragmentGeneralBinding
    private var token: String? = ""
    private var email: String? = ""
    private var id: Int = 0
    private var roleFragment: RoleFragment? = null
    private val managementUserViewModel: ManagementUserViewModel by viewModels {
        ManagementUserViewModelFactory(getManagementUserRepo())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentGeneralBinding.inflate(inflater, container, false)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        return binding.root

    }

    private fun getManagementUserRepo(): ManagementUserRepository {
        val apiService = ApiConfig.getApiService()
        return ManagementUserRepository(apiService)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            token = it.getString(TOKEN)
            id = it.getInt(ID)
            email = requireActivity().intent.getStringExtra(EMAIL)
        }

        Log.d("Emailnyo", email.toString())
        textChanged()

        binding.roleBtn.setOnClickListener {
            roleFragment = RoleFragment().apply {
                setRoleSelected(this@GeneralFragment)
                arguments = Bundle().apply {
                    putString(RoleFragment.CURRENT_ROLE, getCurrentRole())
                }
            }
            roleFragment?.show(childFragmentManager, RoleFragment.TAG)
        }

        binding.simpanData.setOnClickListener {
            val nama = binding.namaEditText.text.toString().trim()
            val email = binding.emailEditText.text.toString().trim()
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            val status = roleFragment?.getSelectedStatus()

            if (nama.isEmpty()) {
                binding.namaEditTextLayout.error =
                    "The field is required"
            } else if (email.isEmpty()) {
                binding.emailEditTextLayout.error =
                    "The field is required"
            } else if (!email.matches(emailPattern.toRegex())) {
                binding.emailEditTextLayout.error =
                    "The field is not a valid email address"
            } else if (email == this.email){
                binding.emailEditTextLayout.error =
                    "The email address already exists"
            } else if (status == null) {
                binding.roleErrorTextView.text = "Pilih Role terlebih dahulu"
                binding.roleErrorTextView.visibility = View.VISIBLE
                binding.roleBtn.error = "The field is required"
            } else {
                lifecycleScope.launch {
                    Log.d("statusNyaa", status.toString())
                    managementUserViewModel.updateUser(
                        token.toString(),
                        id,
                        nama,
                        email,
                        status.toString()
                    ).collect { result ->
                        result.onSuccess {
                            Toast.makeText(
                                requireContext(),
                                "Data berhasil diupdate",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(
                                requireContext(),
                                ManageUserAdminActivity::class.java
                            ).apply {
                                putExtra(ManageUserAdminActivity.TOKEN, token)
                            }
                            requireActivity().startActivity(intent)
                            requireActivity().finish()
                        }
                        result.onFailure {
                            Toast.makeText(
                                requireContext(),
                                "Data gagal diupdate",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    }
                }
            }
        }
    }

    private fun textChanged() {
        binding.namaEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.namaEditTextLayout.error = null
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.emailEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.emailEditTextLayout.error = null
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.roleBtn.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.roleBtn.error = null
                binding.roleErrorTextView.visibility = View.GONE
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun getCurrentRole(): String {
        return when (binding.roleBtn.text.toString()) {
            "Kasir" -> "cashier"
            "Tenant" -> "user_editor"
            "Admin" -> "admin"
            else -> ""
        }
    }

    override fun RoleSelected(status: String) {
        if (status == "cashier") {
            binding.roleBtn.text = "Kasir"
        } else if (status == "user_editor") {
            binding.roleBtn.text = "Tenant"
        }
    }

    companion object {
        const val TOKEN = "token"
        const val ID = "id"
        const val EMAIL = "email"
    }
}