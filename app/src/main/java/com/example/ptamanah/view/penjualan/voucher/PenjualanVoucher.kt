package com.example.ptamanah.view.penjualan.voucher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ptamanah.R
import com.example.ptamanah.databinding.FragmentKategoriBinding
import com.example.ptamanah.databinding.FragmentPenjualanVoucherBinding
import com.example.ptamanah.view.produk.kategori.TambahKategoriFragment

class PenjualanVoucher : Fragment() {
    private var _binding: FragmentPenjualanVoucherBinding? = null
    private val binding get() = _binding!!

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

    }

    private fun navigateToTambahVoucherFragment() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.containerToko, TambahVoucher())
        transaction.addToBackStack(null)
        transaction.commit()
    }


}