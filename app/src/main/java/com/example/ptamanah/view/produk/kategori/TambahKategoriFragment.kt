package com.example.ptamanah.view.produk.kategori

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ptamanah.R
import com.example.ptamanah.databinding.FragmentEditBannerBinding
import com.example.ptamanah.databinding.FragmentTambahKategoriBinding

class TambahKategoriFragment : Fragment() {
    private var _binding: FragmentTambahKategoriBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTambahKategoriBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backTambahKategori.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}