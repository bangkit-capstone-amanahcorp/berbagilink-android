package com.example.ptamanah.view.settings.halaman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.example.ptamanah.R
import com.example.ptamanah.adapter.PreviewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class PreviewFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_preview, container, false)

        val sectionsPagerAdapter = PreviewPagerAdapter(this)
        val viewPager: ViewPager2 = view.findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = view.findViewById(R.id.tabs)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            val tabView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_tab, null)
            val tabIcon = tabView.findViewById<ImageView>(R.id.tabIcon)
            val tabText = tabView.findViewById<TextView>(R.id.tabText)

            tabIcon.setImageResource(TAB_ICONS[position])
            tabText.setText(TAB_TITLES[position])

            tab.customView = tabView
        }.attach()

        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val tabView = tab.customView
                tabView?.isSelected = true
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                val tabView = tab.customView
                tabView?.isSelected = false
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })



        return view
    }



    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.link,
            R.string.toko_online
        )

        @DrawableRes
        private val TAB_ICONS = intArrayOf(
            R.drawable.ic_newspaper,
            R.drawable.ic_shop
        )
    }

}