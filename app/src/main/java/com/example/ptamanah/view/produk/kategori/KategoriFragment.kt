package com.example.ptamanah.view.produk.kategori

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ptamanah.R
import com.example.ptamanah.databinding.FragmentDaftarBannerBinding
import com.example.ptamanah.databinding.FragmentKategoriBinding
import com.example.ptamanah.view.settings.banner.TambahBanner

class KategoriFragment : Fragment() {
    private var _binding: FragmentKategoriBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKategoriBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnTambahKategori.setOnClickListener {
            navigateToTambahKategoriFragment()
        }
    }

    private fun navigateToTambahKategoriFragment() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.containerToko, TambahKategoriFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

}