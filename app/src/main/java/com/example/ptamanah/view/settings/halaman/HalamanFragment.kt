package com.example.ptamanah.view.settings.halaman

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ExpandableListView
import com.example.ptamanah.R


class HalamanFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_halaman, container, false)

        val expandableListView = view.findViewById<ExpandableListView>(R.id.expandableListView)
        val groupList = listOf("Toko", "Media Sosial Toko", "Transaksi")
        val childList = mapOf(
            "Toko" to TokoFragment(),
            "Medial Sosial Toko" to MediaSosialTokoFragment(),
            "Transaksi" to TransaksiFragment()
        )

//        val adapter = DropdownAdapter(requireContext(), groupList, childList, childFragmentManager)
//        expandableListView.setAdapter(adapter)

        val previewButton = view.findViewById<Button>(R.id.previewBtn)
        previewButton.setOnClickListener {
            navigateToPreviewFragment()
        }


        return view
    }

    private fun navigateToPreviewFragment() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.containerToko, PreviewFragment()) // Ganti R.id.fragmentContainer dengan ID container di activity
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addFragment(TokoFragment(), R.id.containerTokoFragment)

        addFragment(MediaSosialTokoFragment(), R.id.containerMediaSosialFragment)

        addFragment(TransaksiFragment(), R.id.containerTransaksiFragment)
    }

    private fun addFragment(fragment: Fragment, containerId: Int) {
        childFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .commit()
    }

}