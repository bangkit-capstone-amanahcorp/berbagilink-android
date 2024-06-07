package com.example.ptamanah.view.admin.manageuser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ptamanah.R
import com.example.ptamanah.databinding.FragmentFilterRoleBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterRole: BottomSheetDialogFragment() {
    private lateinit var binding: FragmentFilterRoleBinding
    private var listener:OnFilterSelectedListener? = null
    private var selectedRole:String? = null


    fun setOnFilterSelectedListener(listener: OnFilterSelectedListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFilterRoleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.radioGroupRole.setOnCheckedChangeListener { _, roleId ->
            selectedRole = when (roleId) {
                R.id.kasir -> "Kasir"
                R.id.admin -> "Admin"
                R.id.tenant -> "Tenant"
                else -> null
            }
        }

        binding.btnOk.setOnClickListener {
            selectedRole?.let {
                listener?.onFilterSelected(it)
            }
            dismiss()
        }
    }

    interface OnFilterSelectedListener {
        fun onFilterSelected(role: String)
    }
}
