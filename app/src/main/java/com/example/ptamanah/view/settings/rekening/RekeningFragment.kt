package com.example.ptamanah.view.settings.rekening

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.example.ptamanah.R
import com.example.ptamanah.view.settings.halaman.PreviewFragment

class RekeningFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_rekening, container, false)

        val tambahRekeningButton = view.findViewById<Button>(R.id.TambahRekening)
        tambahRekeningButton.setOnClickListener {
            navigateToTambahRekeningFragment()
        }

        val editButton = view.findViewById<ImageView>(R.id.EditRekening)
        editButton.setOnClickListener {
            navigateToEditRekening()
        }

        return view
    }

    private fun navigateToEditRekening() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.containerToko, EditRekeningFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun navigateToTambahRekeningFragment() {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.containerToko, TambahRekeningFragment())
        transaction.addToBackStack(null)
        transaction.commit()
    }


}