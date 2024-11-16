package com.example.ptamanah.view.marketing

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ptamanah.R
import com.example.ptamanah.databinding.FragmentAnggotaBinding

class AnggotaFragment : Fragment() {

    private var _binding: FragmentAnggotaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnggotaBinding.inflate(inflater, container, false)

        binding.btnTambahVoucher.setOnClickListener {
            navigateToTambahAnggotaFragment()
        }

        return binding.root

    }

    private fun navigateToTambahAnggotaFragment() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.containerToko, TambahAnggotaFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}