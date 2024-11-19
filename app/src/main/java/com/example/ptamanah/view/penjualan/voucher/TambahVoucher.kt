package com.example.ptamanah.view.penjualan.voucher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.ptamanah.R
import com.example.ptamanah.data.repository.VoucherRepository
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.FragmentTambahKategoriBinding
import com.example.ptamanah.databinding.FragmentTambahVoucherBinding
import com.example.ptamanah.viewModel.factory.VoucherViewModelFactory
import com.example.ptamanah.viewModel.voucher.TambahVoucherViewModel
import com.example.ptamanah.viewModel.voucher.VoucherViewModel

class TambahVoucher : Fragment() {
    private var _binding: FragmentTambahVoucherBinding? = null
    private val binding get() = _binding!!

    private var token: String? = ""

    private val tambahVoucherViewModel: TambahVoucherViewModel by viewModels {
        VoucherViewModelFactory(VoucherRepository(ApiConfig.getApiService()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTambahVoucherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        token =
            "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2JlcmJhZ2kubGluay9hcGkvYXV0aC9sb2dpbiIsImlhdCI6MTczMTk0MDI4MSwiZXhwIjoxNzM0NTY4MjgxLCJuYmYiOjE3MzE5NDAyODEsImp0aSI6InhnazYwMTdPRVlXYmh6ZXciLCJzdWIiOjE1MTQyLCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.o7QMrov0EsJCePcnk5f4khQ6XroRha1PwX23tbR5lRI"

        tambahVoucherViewModel.addVoucherResult.observe(viewLifecycleOwner) { result ->
            result.onSuccess { response ->
                Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
            }.onFailure { exception ->
                Toast.makeText(requireContext(), "Failed to add voucher: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        }

        setupDropdown()
        initButton()
    }

    private fun setupDropdown() {
        val type = resources.getStringArray(R.array.jenis_voucher)
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, type)
        binding.autoCompleteTipeVoucher.setAdapter(adapter)

        binding.autoCompleteTipeVoucher.setOnItemClickListener { parent, view, position, id ->
            val selectedType = parent.getItemAtPosition(position) as String
            binding.autoCompleteTipeVoucher.setText(selectedType, false)
        }
    }

    private fun initButton() {
        binding.btnSave.setOnClickListener {
            val name = binding.etNamaVoucher.text.toString()
            val code = binding.etKodeVoucher.text.toString()
            val type = binding.autoCompleteTipeVoucher.text.toString()
            val feature = "Some Feature"
            val usage = "Some Usage"
            val value = binding.etInputDiskon.text.toString().toFloatOrNull() ?: 0f
            val minValue = 0
            val maxValue = 0
            val quota = binding.etInputKuota.text.toString().toIntOrNull() ?: 0
            val used = 0
            val description = "Some Description"
            val isActive = true

            tambahVoucherViewModel.addVoucher(token!!, name, code, type, feature, usage, value, minValue, maxValue, quota, used, description, isActive)
        }
        binding.backBtn.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.btnCancel.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

}