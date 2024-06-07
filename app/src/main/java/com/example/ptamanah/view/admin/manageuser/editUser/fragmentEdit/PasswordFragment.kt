package com.example.ptamanah.view.admin.manageuser.editUser.fragmentEdit

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextWatcher
import android.text.style.ImageSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ptamanah.R
import com.example.ptamanah.data.repository.ManagementUserRepository
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.FragmentPasswordBinding
import com.example.ptamanah.view.admin.manageuser.ManageUserAdminActivity
import com.example.ptamanah.viewModel.factory.ManagementUserViewModelFactory
import com.example.ptamanah.viewModel.managementuser.ManagementUserViewModel
import kotlinx.coroutines.launch

class PasswordFragment : Fragment() {

    private lateinit var binding: FragmentPasswordBinding
    private var token: String? = ""
    private var id: Int = 0
    private val managementUserViewModel: ManagementUserViewModel by viewModels {
        ManagementUserViewModelFactory(getManagementUserRepo())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordBinding.inflate(inflater, container, false)
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
        }

        textChanged()

        binding.simpanData.setOnClickListener {
            val passBaru = binding.passBaruEditText.text.toString().trim()
            val konfirmasiPass = binding.passConfirmEditText.text.toString().trim()
            val kriteriaPass = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$"

            if (passBaru.isEmpty()) {
                binding.passBaruEditTextLayout.error =
                    getIconizedText(" Password wajib diisi", R.drawable.ic_error)
            } else if (!passBaru.matches(kriteriaPass.toRegex())) {
                binding.passBaruEditTextLayout.error =
                    getIconizedText(" Password belum memenuhi kriteria", R.drawable.ic_error)
            } else if (konfirmasiPass.isEmpty()) {
                binding.passConfirmEditTextLayout.error =
                    getIconizedText(" Konfirmasi Password wajib diisi", R.drawable.ic_error)
            } else if (konfirmasiPass != passBaru) {
                binding.passConfirmEditTextLayout.error =
                    getIconizedText(" Password belum sesuai", R.drawable.ic_error)
            } else if (passBaru.length  < 8) {
                binding.passBaruEditTextLayout.error =
                    getIconizedText(" Password minimal 8 karakter", R.drawable.ic_error)
            } else {
                lifecycleScope.launch {
                    managementUserViewModel.changePassword(token.toString(), id, passBaru, konfirmasiPass).collect { result ->
                        result.onSuccess {
                            Toast.makeText(requireContext(), "Password berhasil diubah", Toast.LENGTH_SHORT).show()
                            val intent = Intent(requireContext(), ManageUserAdminActivity::class.java)
                            intent.putExtra(ManageUserAdminActivity.TOKEN, token)
                            requireActivity().startActivity(intent)
                            requireActivity().finish()
                        }
                        result.onFailure {
                            Toast.makeText(requireContext(), "Password gagal diubah", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    fun getIconizedText(text: String, drawableId: Int): SpannableString {
        val drawable: Drawable? = ContextCompat.getDrawable(requireContext(), drawableId)
        drawable?.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val imageSpan = drawable?.let { ImageSpan(it, ImageSpan.ALIGN_BOTTOM) }

        val spannableString = SpannableString(" $text")
        imageSpan?.let { spannableString.setSpan(it, 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE) }

        return spannableString
    }

    private fun textChanged() {
        binding.passBaruEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.passBaruEditTextLayout.error = null
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val passBaru = s.toString().trim()
                val kriteriaPass = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$"

                if (passBaru.matches(kriteriaPass.toRegex())) {
                    binding.passBaruEditTextLayout.helperText = getIconizedText(" Password memenuhi kriteria", R.drawable.ic_success)
                    binding.passBaruEditTextLayout.boxStrokeColor = ContextCompat.getColor(requireContext(), R.color.green_berhasil)
                    binding.passBaruEditTextLayout.setHelperTextColor(ColorStateList.valueOf(ResourcesCompat.getColor(resources, R.color.green_berhasil, null)))
                } else {
                    binding.passBaruEditTextLayout.error = getIconizedText(" Password belum memenuhi kriteria", R.drawable.ic_error)
                    binding.passBaruEditTextLayout.helperText = null
                    binding.passBaruEditTextLayout.isHelperTextEnabled = false
                    binding.passBaruEditTextLayout.errorIconDrawable = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.passConfirmEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                binding.passConfirmEditTextLayout.error = null
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val passBaru = binding.passBaruEditText.text.toString().trim()
                val konfirmasiPass = s.toString().trim()

                if (konfirmasiPass == passBaru) {
                    binding.passConfirmEditTextLayout.helperText = getIconizedText(" Password sesuai", R.drawable.ic_success)
                    binding.passConfirmEditTextLayout.boxStrokeColor = ContextCompat.getColor(requireContext(), R.color.green_berhasil)
                    binding.passConfirmEditTextLayout.setHelperTextColor(ColorStateList.valueOf(ResourcesCompat.getColor(resources, R.color.green_berhasil, null)))
                } else {
                    binding.passConfirmEditTextLayout.error = getIconizedText(" Password belum sesuai", R.drawable.ic_error)
                    binding.passConfirmEditTextLayout.helperText = null
                    binding.passConfirmEditTextLayout.isHelperTextEnabled = false
                    binding.passConfirmEditTextLayout.errorIconDrawable = null
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    companion object {
        const val TOKEN = "token"
        const val ID = "id"
    }
}