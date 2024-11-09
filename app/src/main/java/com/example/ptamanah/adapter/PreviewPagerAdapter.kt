package com.example.ptamanah.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ptamanah.view.settings.halaman.PreviewFragment
import com.example.ptamanah.view.settings.halaman.TokoOnlineFragment

class PreviewPagerAdapter(fragment: PreviewFragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TokoOnlineFragment()
            1 -> TokoOnlineFragment()
            else -> throw IllegalStateException("Position $position is invalid for this adapter")
        }
    }
}