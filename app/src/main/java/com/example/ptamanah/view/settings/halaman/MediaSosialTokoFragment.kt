package com.example.ptamanah.view.settings.halaman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ptamanah.R
import com.example.ptamanah.databinding.FragmentMediaSosialTokoBinding

class MediaSosialTokoFragment : Fragment() {
    private var _binding: FragmentMediaSosialTokoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMediaSosialTokoBinding.inflate(inflater, container, false)

        binding.titleMediaSosial.setOnClickListener {
            if (binding.containerMediaSosial.visibility == View.GONE) {
                binding.containerMediaSosial.visibility = View.VISIBLE
                binding.iconArrow.setImageResource(R.drawable.baseline_keyboard_arrow_down_24)
            } else {
                binding.containerMediaSosial.visibility = View.GONE
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