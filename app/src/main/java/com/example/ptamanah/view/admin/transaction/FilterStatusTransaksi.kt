package com.example.ptamanah.view.admin.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ptamanah.R
import com.example.ptamanah.databinding.FragmentFilteringStatusTransaksiBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterStatusTransaksi : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilteringStatusTransaksiBinding
    private var listener: OnFilterSelectedListener? = null
    private var selectedStatus: String? = null

    fun setOnFilterSelectedListener(listener: OnFilterSelectedListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilteringStatusTransaksiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedStatus = when (checkedId) {
                R.id.smua -> ""
                R.id.paid -> "PAID"
                R.id.unpaid -> "UNPAID"
                R.id.confirmed -> "CONFIRMED"
                R.id.failed -> "FAILED"
                else -> null
            }
        }

        binding.resetBtn.setOnClickListener {
            selectedStatus = ""
            listener?.onFilterSelected(selectedStatus ?: "")
            dismiss()
        }

        binding.okeBtn.setOnClickListener {
            selectedStatus?.let {
                listener?.onFilterSelected(it)
            }
            dismiss()
        }
    }

    interface OnFilterSelectedListener {
        fun onFilterSelected(status: String)
    }

    companion object {
        const val TAG = "FilterStatusTransaksi"
    }
}
