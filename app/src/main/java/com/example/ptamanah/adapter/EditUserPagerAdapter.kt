package com.example.ptamanah.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ptamanah.view.admin.manageuser.editUser.fragmentEdit.GeneralFragment
import com.example.ptamanah.view.admin.manageuser.editUser.fragmentEdit.PasswordFragment
import com.example.ptamanah.view.myEvent.MyEventFragment

class EditUserPagerAdapter(activity: AppCompatActivity, private val token: String, private val idnya: Int) :
    FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> GeneralFragment().apply {
                arguments = Bundle().apply {
                    putString(GeneralFragment.TOKEN, token)
                    putInt(GeneralFragment.ID, idnya)
                }
            }
            1 -> PasswordFragment().apply {
                arguments = Bundle().apply {
                    putString(PasswordFragment.TOKEN, token)
                    putInt(PasswordFragment.ID, idnya)
                }
            }
            else -> throw IllegalStateException("Unexpected position: $position")
        }
    }
}