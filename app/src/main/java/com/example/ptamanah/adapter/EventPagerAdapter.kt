package com.example.ptamanah.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ptamanah.view.myEvent.MyEventFragment


class EventPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = MyEventFragment()
        fragment.arguments = Bundle().apply {
            putInt(MyEventFragment.ARG_POSITION, position + 1)
        }
        return fragment
    }


}