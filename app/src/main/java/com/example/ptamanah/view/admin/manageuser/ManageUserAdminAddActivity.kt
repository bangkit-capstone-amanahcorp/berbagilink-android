package com.example.ptamanah.view.admin.manageuser

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ImageSpan
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
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

        setupAction()
        setupActionBar()


        binding.btnRole.setOnClickListener{
            roleFragment = RoleFragment().apply {
                setRoleSelected(this@ManageUserAdminAddActivity)
                arguments = Bundle().apply {
                    putString(RoleFragment.CURRENT_ROLE, getCurrentRole())
                }
            }
            roleFragment?.show(supportFragmentManager, RoleFragment.TAG)
        }
    }

    private fun setupAction() {
        textChanged()

        binding.btnsimpan.setOnClickListener {
            val name = binding.edRegisterName.text.toString().trim()
            val email = binding.edRegisterEmail.text.toString().trim()
            val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
            val password = binding.edRegisterPassword.text.toString().trim()
            val kriteriaPass = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$"
            val passwordConfirmation = binding.edRegisterConfirmPassword.text.toString().trim()
            val role = roleFragment?.getSelectedStatus()

            if (name.isEmpty()) {
                binding.tilRegisterName.error = "Nama harus diisi"
            } else if (email.isEmpty()) {
                binding.tilRegisterEmail.error = "Email harus diisi"
            } else if (!email.matches(emailPattern.toRegex())) {
                binding.tilRegisterEmail.error = "Email tidak valid"
            } else if (role.isNullOrEmpty()) {
                binding.roleErrorTextView.text = "Pilih role terlebih dahulu"
                binding.roleErrorTextView.visibility = View.VISIBLE
                binding.btnRole.error = "Role harus dipilih"
            } else if (password.isEmpty()) {
                binding.tilRegisterPassword.error = "Password harus diisi"
            } else if (!password.matches(kriteriaPass.toRegex())) {
                binding.tilRegisterPassword.error = getIconizedText( "Password belum memenuhi kriteria", R.drawable.ic_error)
            } else if (password.length < 8) {
                binding.tilRegisterPassword.error = "Password minimal 8 karakter"
            } else if (passwordConfirmation.isEmpty()) {
                binding.tilRegisterConfiemPassword.error = "Konfirmasi password harus diisi"
            } else if (passwordConfirmation != password) {
                binding.tilRegisterConfiemPassword.error = "Password belum sesuai"
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

    private fun textChanged() {
        binding.edRegisterName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.tilRegisterName.error = null
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.edRegisterEmail.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.tilRegisterEmail.error = null
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val email = s.toString().trim()
                val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

                if (email.matches(emailPattern.toRegex())) {
                    binding.tilRegisterEmail.error = null
                } else {
                    binding.tilRegisterEmail.error = "Email tidak valid"
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.btnRole.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.btnRole.error = null
                binding.roleErrorTextView.visibility = View.GONE
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.edRegisterPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.tilRegisterPassword.error = null
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pass = s.toString().trim()
                val kriteriaPass = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$"

                if (pass.matches(kriteriaPass.toRegex())) {
                    binding.tilRegisterPassword.helperText = getIconizedText(" Password memenuhi kriteria", R.drawable.ic_success)
                    binding.tilRegisterPassword.boxStrokeColor = ContextCompat.getColor(this@ManageUserAdminAddActivity, R.color.green_berhasil)
                    binding.tilRegisterPassword.setHelperTextColor(ColorStateList.valueOf(ResourcesCompat.getColor(resources, R.color.green_berhasil, null)))
                } else {
                    binding.tilRegisterPassword.error = getIconizedText(" Password belum memenuhi kriteria", R.drawable.ic_error)
                    binding.tilRegisterPassword.helperText = null
                    binding.tilRegisterPassword.isHelperTextEnabled = false
                    binding.tilRegisterPassword.errorIconDrawable = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

        binding.edRegisterConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.tilRegisterConfiemPassword.error = null
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val pass = binding.edRegisterPassword.text.toString().trim()
                val confirmPass = s.toString().trim()

                if (confirmPass == pass) {
                    binding.tilRegisterConfiemPassword.helperText = getIconizedText(" Password sesuai", R.drawable.ic_success)
                    binding.tilRegisterConfiemPassword.boxStrokeColor = ContextCompat.getColor(this@ManageUserAdminAddActivity, R.color.green_berhasil)
                    binding.tilRegisterConfiemPassword.setHelperTextColor(ColorStateList.valueOf(ResourcesCompat.getColor(resources, R.color.green_berhasil, null)))
                } else {
                    binding.tilRegisterConfiemPassword.error = getIconizedText(" Password belum sesuai", R.drawable.ic_error)
                    binding.tilRegisterConfiemPassword.helperText = null
                    binding.tilRegisterConfiemPassword.isHelperTextEnabled = false
                    binding.tilRegisterConfiemPassword.errorIconDrawable = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })


    }

    fun getIconizedText(text: String, drawableId: Int): SpannableString {
        val drawable: Drawable? = ContextCompat.getDrawable(this, drawableId)
        drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val imageSpan = drawable?.let { ImageSpan(it, ImageSpan.ALIGN_BOTTOM) }

        val spannableString = SpannableString(" $text")
        imageSpan?.let { spannableString.setSpan(it, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) }

        return spannableString
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
        }
    }
    private fun getCurrentRole(): String {
        return when (binding.btnRole.text.toString()) {
            "Kasir" -> "cashier"
            "Tenant" -> "user_editor"
            else -> ""
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupActionBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(
            ColorDrawable(
                ContextCompat.getColor(
                    this,
                    androidx.cardview.R.color.cardview_light_background
                )
            )
        )
        supportActionBar?.title = "Tambahkan User"
    }
    companion object {
        const val TOKEN = "token"
    }
}