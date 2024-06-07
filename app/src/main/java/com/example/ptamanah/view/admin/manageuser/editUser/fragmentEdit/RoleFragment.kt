package com.example.ptamanah.view.admin.manageuser.editUser.fragmentEdit

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ptamanah.R
import com.example.ptamanah.databinding.FragmentRoleBinding
import com.example.ptamanah.view.admin.logcheck.FilteringStatus
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RoleFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentRoleBinding
    private var listener: RoleSelectedListener? = null
    private var selectedStatus: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentRole = arguments?.getString(CURRENT_ROLE)
        selectedStatus = currentRole

        binding.radioGroup.setOnCheckedChangeListener{_, roleId ->
            selectedStatus = when (roleId) {
                R.id.kasir -> "cashier"
                R.id.admin -> "admin" // admin masih belum tau valuenya
                R.id.tenant -> "user_editor"
                else -> null
            }
        }

        binding.okeBtn.setOnClickListener {
            val hasil = getSelectedStatus()
            Log.d("RoleFragment", "Selected status: $hasil")

            Log.d("RoleFragment1", "Selected status: $selectedStatus")
            selectedStatus?.let {
                listener?.RoleSelected(it)
                Log.d("RoleFragment2", "Selected status: $selectedStatus")
            }
            dismiss()
        }

    }

    fun getSelectedStatus(): String? {
        return selectedStatus
    }

    fun setRoleSelected(listener: RoleSelectedListener) {
        this.listener = listener
    }

    interface RoleSelectedListener {
        fun RoleSelected(status: String)
    }

    companion object {
        const val TAG = "RoleFragment"
        const val CURRENT_ROLE = "current_role"
    }

}