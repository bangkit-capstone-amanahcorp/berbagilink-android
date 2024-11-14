package com.example.ptamanah.view.settings.banner

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.ptamanah.R
import com.example.ptamanah.databinding.FragmentDaftarBannerBinding
import com.example.ptamanah.view.settings.banner.popup.AktifkanBanner
import com.example.ptamanah.view.settings.banner.popup.NonaktifBanner

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
        binding.ivDelete.setOnClickListener {
            showDeleteDialog("Banner 11.11")
        }

        // Listener for the first switch
        binding.materialSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                showActiveDialog("Banner 11.11")
            } else {
                showNonActiveDialog("Banner 11.11")
            }
        }

        // Listener for the second switch
        binding.materialSwitch2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                showActiveDialog("Banner 11.11")
            } else {
                showNonActiveDialog("Banner 11.11")
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

    private fun showNonActiveDialog(itemName: String) {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_non_active_confirmation, null)
        dialogBuilder.setView(dialogView)

        val tvMessage = dialogView.findViewById<TextView>(R.id.tvMessage)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
        val btnCancel1 = dialogView.findViewById<ImageButton>(R.id.btnClose)
        val btnDelete = dialogView.findViewById<Button>(R.id.btnNonaktif)

        tvMessage.text = "Anda yakin akan me-nonaktifkan \"$itemName\" ?"

        val alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        btnCancel1.setOnClickListener {
            alertDialog.dismiss()
        }

        btnDelete.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    private fun showActiveDialog(itemName: String) {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_active_confimation, null)
        dialogBuilder.setView(dialogView)

        val tvMessage = dialogView.findViewById<TextView>(R.id.tvMessage)
        val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)
        val btnCancel1 = dialogView.findViewById<ImageButton>(R.id.btnClose)
        val btnActive = dialogView.findViewById<Button>(R.id.btnActive)

        tvMessage.text = "Anda yakin akan mengaktifkan \"$itemName\" ?"

        val alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

        btnCancel.setOnClickListener {
            alertDialog.dismiss()
        }
        btnCancel1.setOnClickListener {
            alertDialog.dismiss()
        }

        btnActive.setOnClickListener {
            alertDialog.dismiss()
        }
    }

    private fun showDeleteDialog(itemName: String) {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.dialog_delete_confirmation, null)
        dialogBuilder.setView(dialogView)

        val tvMessage = dialogView.findViewById<TextView>(R.id.tvMessage)
        val btnCancel1 = dialogView.findViewById<ImageButton>(R.id.btnClose)
        val btnCancel2 = dialogView.findViewById<Button>(R.id.btnCancel)
        val btnDelete = dialogView.findViewById<Button>(R.id.btnDelete)

        tvMessage.text = "Anda yakin akan menghapus \"$itemName\" ?"

        val alertDialog = dialogBuilder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog.show()

        btnCancel1.setOnClickListener {
            alertDialog.dismiss()
        }
        btnCancel2.setOnClickListener {
            alertDialog.dismiss()
        }
        btnDelete.setOnClickListener {
            alertDialog.dismiss()
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
