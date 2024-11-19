package com.example.ptamanah.view.penjualan.voucher

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.example.ptamanah.R
import com.example.ptamanah.data.repository.VoucherRepository
import com.example.ptamanah.data.retrofit.ApiConfig
import com.example.ptamanah.databinding.FragmentPenjualanVoucherBinding
import com.example.ptamanah.viewModel.factory.VoucherViewModelFactory
import com.example.ptamanah.viewModel.voucher.VoucherViewModel

class PenjualanVoucher : Fragment() {
    private var _binding: FragmentPenjualanVoucherBinding? = null
    private val binding get() = _binding!!

    private var token: String? = ""

    private val voucherViewModel: VoucherViewModel by viewModels {
        VoucherViewModelFactory(VoucherRepository(ApiConfig.getApiService()))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPenjualanVoucherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tambahVoucher.setOnClickListener {
            navigateToTambahVoucherFragment()
        }
        token =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2JlcmJhZ2kubGluay9hcGkvYXV0aC9sb2dpbiIsImlhdCI6MTczMTk0MDI4MSwiZXhwIjoxNzM0NTY4MjgxLCJuYmYiOjE3MzE5NDAyODEsImp0aSI6InhnazYwMTdPRVlXYmh6ZXciLCJzdWIiOjE1MTQyLCJwcnYiOiIyM2JkNWM4OTQ5ZjYwMGFkYjM5ZTcwMWM0MDA4NzJkYjdhNTk3NmY3In0.o7QMrov0EsJCePcnk5f4khQ6XroRha1PwX23tbR5lRI"
        showAllVoucher()

    }

    private fun showAllVoucher() {
        voucherViewModel.vouchers.observe(viewLifecycleOwner) { result ->
            result.onSuccess { responseListVoucher ->
                val vouchers = responseListVoucher.voucher ?: emptyList()

                // Kosongkan TableLayout sebelum menambahkan data baru
                binding.tbVoucher.removeViewsInLayout(1, binding.tbVoucher.childCount - 1)

                // Tambahkan baris header jika belum ada
                if (binding.tbVoucher.childCount == 1) {
                    // Tambahkan header jika belum ada
                    val headerRow = LayoutInflater.from(requireContext())
                        .inflate(R.layout.list_voucher_row, binding.tbVoucher, false)
                    binding.tbVoucher.addView(headerRow)
                }

                vouchers.forEachIndexed { index, voucher ->
                    val tableRow = LayoutInflater.from(requireContext())
                        .inflate(R.layout.list_voucher_row, binding.tbVoucher, false)

                    val noTextView: TextView = tableRow.findViewById(R.id.noVoucher)
                    val namaVoucherTextView: TextView = tableRow.findViewById(R.id.nama_voucher)
                    val kodeTextView: TextView = tableRow.findViewById(R.id.kode)
                    val tipeTextView: TextView = tableRow.findViewById(R.id.tipe)
                    val nilaiVoucherTextView: TextView = tableRow.findViewById(R.id.nilai_voucher)
                    val kuotaTextView: TextView = tableRow.findViewById(R.id.kuota)
                    val tglMulaiTextView: TextView = tableRow.findViewById(R.id.tgl_mulai)
                    val tglBerakhirTextView: TextView = tableRow.findViewById(R.id.tgl_berakhir)

                    noTextView.text = (index + 1).toString()
                    namaVoucherTextView.text = voucher.voucher_name
                    kodeTextView.text = voucher.voucher_code
                    tipeTextView.text = voucher.voucher_type
                    nilaiVoucherTextView.text = "${voucher.voucher_value}%"
                    kuotaTextView.text = voucher.quota.toString()
                    tglMulaiTextView.text = "12/09/2024"
                    tglBerakhirTextView.text = "12/09/2024"

                    binding.tbVoucher.addView(tableRow)
                }
            }.onFailure { exception ->
                Log.e("VoucherError", "Error fetching vouchers: ${exception.message}")
            }
        }

        if (voucherViewModel.vouchers.value == null) {
            voucherViewModel.getAllVoucher(token.toString())
        }
    }

    private fun navigateToTambahVoucherFragment() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.containerToko, TambahVoucher())
        transaction.addToBackStack(null)
        transaction.commit()
    }


}