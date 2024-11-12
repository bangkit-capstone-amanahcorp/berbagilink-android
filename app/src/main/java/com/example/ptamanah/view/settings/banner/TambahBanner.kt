package com.example.ptamanah.view.settings.banner

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import com.example.ptamanah.R
import com.google.android.material.appbar.MaterialToolbar
import androidx.drawerlayout.widget.DrawerLayout

class TambahBanner : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tambah_banner, container, false)

//        // Initialize the Toolbar
//        val toolbar = view.findViewById<MaterialToolbar>(R.id.topAppBarBanner)
//
//        // Initialize the DrawerLayout
//        val drawerLayout = activity?.findViewById<DrawerLayout>(R.id.drawer_layout)
//
//        // Set navigation click listener to open/close the drawer
//        toolbar.setNavigationOnClickListener {
//            if (drawerLayout?.isDrawerOpen(GravityCompat.START) == true) {
//                drawerLayout.closeDrawer(GravityCompat.START)
//            } else {
//                drawerLayout?.openDrawer(GravityCompat.START)
//            }
//        }

        // Inflate the layout for this fragment
        return view
    }


}
