package com.example.ptamanah.view.produk.daftarProduk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.ptamanah.R
import com.example.ptamanah.databinding.FragmentInformasiProdukBinding

class InformasiProdukFragment : Fragment() {

    private var _binding: FragmentInformasiProdukBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInformasiProdukBinding.inflate(inflater, container, false)

        val locationsArray = resources.getStringArray(R.array.list_provinsi)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, locationsArray)

//        (binding.textFieldProvinsi.editText as? MaterialAutoCompleteTextView)?.setAdapter(adapter)

        binding.titleInformasiProduk.setOnClickListener {
            if (binding.containerInformasiProdukContent.visibility == View.GONE) {
                binding.containerInformasiProdukContent.visibility = View.VISIBLE
                binding.iconArrow.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
            } else {
                binding.containerInformasiProdukContent.visibility = View.GONE
                binding.iconArrow.setImageResource(R.drawable.baseline_keyboard_arrow_up_24)
            }
        }

        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}