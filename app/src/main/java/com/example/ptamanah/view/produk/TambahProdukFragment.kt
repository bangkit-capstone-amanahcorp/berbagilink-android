package com.example.ptamanah.view.produk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ptamanah.R
import com.example.ptamanah.view.settings.halaman.MediaSosialTokoFragment
import com.example.ptamanah.view.settings.halaman.TokoFragment
import com.example.ptamanah.view.settings.halaman.TransaksiFragment

class TambahProdukFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tambah_produk, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addFragment(TokoFragment(), R.id.containerInformasiProduk)
        addFragment(MediaSosialTokoFragment(), R.id.containerDetailProduk)
        addFragment(TransaksiFragment(), R.id.containerVarian)
        addFragment(TransaksiFragment(), R.id.containerHargaStok)
        addFragment(TransaksiFragment(), R.id.containerDiskonGrosir)
        addFragment(TransaksiFragment(), R.id.containerBerat)
        addFragment(TransaksiFragment(), R.id.containerRekeningTransaksi)
    }

    private fun addFragment(fragment: Fragment, containerId: Int) {
        childFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }


}