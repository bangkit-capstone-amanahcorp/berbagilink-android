package com.example.ptamanah.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ptamanah.R
import com.example.ptamanah.data.response.DataItemManagementUser
import com.example.ptamanah.databinding.ListItemManageUserBinding

class ManagementUserAdapter :
    ListAdapter<DataItemManagementUser, ManagementUserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var onDeleteClickCallBack: OnDeleteClickCallBack? = null
    private var onEditClickCallBack: OnEditClickCallBack? = null

    fun setOnDeleteClickCallBack(onDeleteClickCallBack: OnDeleteClickCallBack) {
        this.onDeleteClickCallBack = onDeleteClickCallBack
    }

    fun setOnEditClickCallBack(onEditClickCallBack: OnEditClickCallBack) {
        this.onEditClickCallBack = onEditClickCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemManageUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            Log.d("adaptertes", "Binding data at position $position: $data")
            holder.bind(data, onDeleteClickCallBack, onEditClickCallBack)
        } else {
            Log.d("kenapadah", "No data found at position $position")
        }
    }

    class MyViewHolder(private val binding: ListItemManageUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            data: DataItemManagementUser,
            onDeleteClickCallBack: OnDeleteClickCallBack?,
            onEditClickCallBack: OnEditClickCallBack?
        ) {
            binding.namaPenggunaTv.text = data.name
            binding.emailTv.text = data.email
            binding.tanggalTv.text = data.createdAtUnix

            //pewarnaan roll user
            when (data.role) {
                "cashier" -> {
                    binding.statusIV.setImageResource(R.drawable.roll_cashier_border)
                    binding.tvStatus.text = "Kasir"
                    binding.tvStatus.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.ungu
                        )
                    )
                }
                "user_editor" -> {
                    binding.statusIV.setImageResource(R.drawable.roll_tenant_border)
                    binding.tvStatus.text = "Editor"
                    binding.tvStatus.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.oren
                        )
                    )
                }
                else -> {
                    binding.tvStatus.text = "Admin"
                }
            }

            //show up button
            itemView.setOnClickListener {
                if (binding.showbutton.visibility == View.VISIBLE) {
                    binding.showbutton.visibility = View.GONE
                    binding.cardView.strokeColor = itemView.context.getColor(R.color.abu_terang)
                } else {
                    binding.showbutton.visibility = View.VISIBLE
                    binding.cardView.strokeColor = itemView.context.getColor(R.color.biru_toska)
                }
            }

            binding.btnDelete.setOnClickListener {
                onDeleteClickCallBack?.onDeleteClicked(data)
            }
            binding.btnEdit.setOnClickListener {
                onEditClickCallBack?.onEditClicked(data)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemManagementUser>() {
            override fun areItemsTheSame(oldItem: DataItemManagementUser, newItem: DataItemManagementUser): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItemManagementUser, newItem: DataItemManagementUser): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnDeleteClickCallBack {
        fun onDeleteClicked(user: DataItemManagementUser)
    }

    interface OnEditClickCallBack {
        fun onEditClicked(user: DataItemManagementUser)
    }
}
