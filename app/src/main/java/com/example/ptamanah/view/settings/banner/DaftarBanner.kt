package com.example.ptamanah.view.settings.banner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.ptamanah.R
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.appbar.MaterialToolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.core.view.GravityCompat
import com.example.ptamanah.databinding.FragmentDaftarBannerBinding
import com.example.ptamanah.view.settings.banner.popup.AktifkanBanner
import com.example.ptamanah.view.settings.banner.popup.HapusBanner
import com.example.ptamanah.view.settings.banner.popup.NonaktifBanner
import com.example.ptamanah.view.settings.rekening.EditRekeningFragment
import com.example.ptamanah.view.settings.rekening.TambahRekeningFragment

class DaftarBanner : Fragment() {
    private var _binding: FragmentDaftarBannerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDaftarBannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.materialSwitch.isChecked = true
        binding.materialSwitch2.isChecked = false

        binding.ivMove.setOnClickListener { onMoveIconClick() }
        binding.ivEdit.setOnClickListener { onEditIconClick() }
        binding.ivDelete.setOnClickListener { onDeleteIconClick() }

        // Listener for the first switch
        binding.materialSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Switch 1 is ON
            } else {
                // Switch 1 is OFF
            }
        }

        // Listener for the second switch
        binding.materialSwitch2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Switch 2 is ON
            } else {
                // Switch 2 is OFF
            }
        }

        // Setup drawer and toolbar navigation
//        val drawerLayout = view.findViewById<DrawerLayout>(R.id.drawer_layout)
//        val toolbar = view.findViewById<MaterialToolbar>(R.id.topAppBarBanner)
//
//
//
//        toolbar.setNavigationOnClickListener {
//            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//                drawerLayout.closeDrawer(GravityCompat.START)
//            } else {
//                drawerLayout.openDrawer(GravityCompat.START)
//            }
//        }

        binding.btnTmbhBanner.setOnClickListener {
            navigateToTambahBannerFragment()
        }

        binding.ivEdit.setOnClickListener {
            navigateToEditBannerFragment()
        }
    }

    private fun navigateToEditBannerFragment() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.containerToko, EditBanner())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun navigateToTambahBannerFragment() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.containerToko, TambahBanner())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun onMoveIconClick() {
        val aktifkanBannerDialog = AktifkanBanner.newInstance("param1_value", "param2_value")
        aktifkanBannerDialog.show(parentFragmentManager, "AktifkanBannerDialog")
        Toast.makeText(context, "Icon Move diklik!", Toast.LENGTH_SHORT).show()
    }

    private fun onEditIconClick() {
        val nonaktifBannerDialog = NonaktifBanner.newInstance("param1_value", "param2_value")
        nonaktifBannerDialog.show(parentFragmentManager, "NonaktifBannerDialog")
        Toast.makeText(context, "Icon Edit diklik!", Toast.LENGTH_SHORT).show()
    }

    private fun onDeleteIconClick() {
        val hapusBannerDialog = HapusBanner.newInstance("param1_value", "param2_value")
        hapusBannerDialog.show(parentFragmentManager, "HapusBannerDialog")
        Toast.makeText(context, "Icon Delete diklik!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
