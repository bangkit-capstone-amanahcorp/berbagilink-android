package com.example.ptamanah.view.penjualan.transaksi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.example.ptamanah.R
import com.google.android.material.checkbox.MaterialCheckBox

class PenjualanTransaksi : Fragment() {

    private lateinit var parentCheckBox: MaterialCheckBox
    private lateinit var childCheckBoxes: List<CheckBox>
    private var isUpdating = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_penjualan_transaksi, container, false)

        parentCheckBox = view.findViewById(R.id.cbox_alltransaksi)
        childCheckBoxes = listOf(
            view.findViewById(R.id.cbox_transaksi),
            view.findViewById(R.id.cbox_transaksi2),
            view.findViewById(R.id.cbox_transaksi3),
            view.findViewById(R.id.cbox_transaksi4)
        )

        setupListeners()

        return view
    }

    private fun setupListeners() {
        parentCheckBox.addOnCheckedStateChangedListener { _, state ->
            if (!isUpdating) {
                isUpdating = true
                val isChecked = state == MaterialCheckBox.STATE_CHECKED
                childCheckBoxes.forEach { it.isChecked = isChecked }
                isUpdating = false
            }
        }

        childCheckBoxes.forEach { childCheckBox ->
            childCheckBox.setOnCheckedChangeListener { _, _ ->
                if (!isUpdating) {
                    updateParentCheckBoxState()
                }
            }
        }
    }

    private fun updateParentCheckBoxState() {
        val checkedCount = childCheckBoxes.count { it.isChecked }
        val allChecked = checkedCount == childCheckBoxes.size
        val noneChecked = checkedCount == 0

        isUpdating = true
        when {
            allChecked -> parentCheckBox.checkedState = MaterialCheckBox.STATE_CHECKED
            noneChecked -> parentCheckBox.checkedState = MaterialCheckBox.STATE_UNCHECKED
            else -> parentCheckBox.checkedState = MaterialCheckBox.STATE_INDETERMINATE
        }
        isUpdating = false
    }
}
