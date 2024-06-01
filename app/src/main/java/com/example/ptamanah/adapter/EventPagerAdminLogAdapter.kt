package com.example.ptamanah.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ptamanah.view.admin.events.ListEventsAdminFragment
import com.example.ptamanah.view.admin.logcheck.EventAdminFragment

class EventPagerAdminLogAdapter(fragmentManager: FragmentManager,lifesycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager,lifesycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = EventAdminFragment()
        fragment.arguments = Bundle().apply {
            putInt(EventAdminFragment.ARG_POSITION, position + 1)
        }
        return fragment
    }
}