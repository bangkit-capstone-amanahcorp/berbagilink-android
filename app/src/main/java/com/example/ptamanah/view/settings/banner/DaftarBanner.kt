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
import com.example.ptamanah.view.settings.banner.popup.AktifkanBanner
import com.example.ptamanah.view.settings.banner.popup.HapusBanner
import com.example.ptamanah.view.settings.banner.popup.NonaktifBanner
import com.example.ptamanah.view.settings.rekening.EditRekeningFragment
import com.example.ptamanah.view.settings.rekening.TambahRekeningFragment

class DaftarBanner : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_daftar_banner, container, false)

        // Access the two MaterialSwitches from XML
        val materialSwitch1 = view.findViewById<MaterialSwitch>(R.id.materialSwitch)
        val materialSwitch2 = view.findViewById<MaterialSwitch>(R.id.materialSwitch2)

        // Set initial checked state if needed
        materialSwitch1.isChecked = true
        materialSwitch2.isChecked = false

        // Setup ImageView click listeners
        val ivMove: ImageView = view.findViewById(R.id.iv_move)
        val ivEdit: ImageView = view.findViewById(R.id.iv_edit)
        val ivDelete: ImageView = view.findViewById(R.id.iv_delete)

        ivMove.setOnClickListener { onMoveIconClick() }
        ivEdit.setOnClickListener { onEditIconClick() }
        ivDelete.setOnClickListener { onDeleteIconClick() }

        // Listener for the first switch
        materialSwitch1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Switch 1 is ON
            } else {
                // Switch 1 is OFF
            }
        }

        // Listener for the second switch
        materialSwitch2.setOnCheckedChangeListener { _, isChecked ->
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

        val tambahBannerButton = view.findViewById<Button>(R.id.btnTmbhBanner)
        tambahBannerButton.setOnClickListener {
            navigateToTambahBannerFragment()
        }

        val editButton = view.findViewById<ImageView>(R.id.iv_edit)
        editButton.setOnClickListener {
            navigateToEditBannerFragment()
        }

        return view
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

    // Define click handlers outside of onCreate
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
}
