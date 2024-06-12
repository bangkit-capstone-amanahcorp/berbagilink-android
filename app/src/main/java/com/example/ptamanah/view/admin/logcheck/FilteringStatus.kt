package com.example.ptamanah.view.admin.logcheck

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ptamanah.R
import com.example.ptamanah.databinding.FragmentFilteringStatusBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilteringStatus : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentFilteringStatusBinding
    private var listener: OnFilterSelectedListener? = null
    private var selectedStatus: String? = null

    fun setOnFilterSelectedListener(listener: OnFilterSelectedListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilteringStatusBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            selectedStatus = when (checkedId) {
                R.id.smua -> ""
                R.id.check -> "check-In"
                R.id.uncheck -> "uncheck"
                R.id.returnCheck -> "return"
                R.id.failed -> "failed"
                else -> null
            }
        }

        binding.resetBtn.setOnClickListener {
            selectedStatus =""
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
}
