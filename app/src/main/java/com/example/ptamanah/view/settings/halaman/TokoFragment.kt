package com.example.ptamanah.view.settings.halaman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.ptamanah.R
import com.example.ptamanah.databinding.FragmentTokoBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView

class TokoFragment : Fragment() {

    private var _binding: FragmentTokoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTokoBinding.inflate(inflater, container, false)

        val locationsArray = resources.getStringArray(R.array.list_provinsi)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, locationsArray)

        (binding.textFieldProvinsi.editText as? MaterialAutoCompleteTextView)?.setAdapter(adapter)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
