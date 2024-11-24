package com.example.ptamanah.view.produk.daftarProduk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ptamanah.R
import com.example.ptamanah.databinding.FragmentJasaTanpaVarianBinding

class JasaTanpaVarianFragment : Fragment() {

    private var _binding: FragmentJasaTanpaVarianBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJasaTanpaVarianBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initExpandableComponent()
    }

    private fun initExpandableComponent() {
        binding.containerInformasiProduk.setOnClickListener {
            if (binding.contentInformasiProduk.visibility == View.GONE) {
                binding.contentInformasiProduk.visibility = View.VISIBLE
                binding.arrowInformasiProduk.setImageResource(R.drawable.ic_arrow_up_toska)
            } else {
                binding.contentInformasiProduk.visibility = View.GONE
                binding.arrowInformasiProduk.setImageResource(R.drawable.ic_arrow_down_toska)
            }
        }

        binding.containerDetailProduk.setOnClickListener {
            if (binding.contentDetailProduk.visibility == View.GONE) {
                binding.contentDetailProduk.visibility = View.VISIBLE
                binding.arrowInformasiProduk.setImageResource(R.drawable.ic_arrow_up_toska)
            } else {
                binding.contentDetailProduk.visibility = View.GONE
                binding.arrowInformasiProduk.setImageResource(R.drawable.ic_arrow_down_toska)
            }
        }

        binding.containerHargaStok.setOnClickListener {
            if (binding.contentHargaStok.visibility == View.GONE) {
                binding.contentHargaStok.visibility = View.VISIBLE
                binding.arrowInformasiProduk.setImageResource(R.drawable.ic_arrow_up_toska)
            } else {
                binding.contentHargaStok.visibility = View.GONE
                binding.arrowInformasiProduk.setImageResource(R.drawable.ic_arrow_down_toska)
            }
        }

        binding.containerRekeningTransaksi.setOnClickListener {
            if (binding.contentRekeningTransaksi.visibility == View.GONE) {
                binding.contentRekeningTransaksi.visibility = View.VISIBLE
                binding.arrowInformasiProduk.setImageResource(R.drawable.ic_arrow_up_toska)
            } else {
                binding.contentRekeningTransaksi.visibility = View.GONE
                binding.arrowInformasiProduk.setImageResource(R.drawable.ic_arrow_down_toska)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
