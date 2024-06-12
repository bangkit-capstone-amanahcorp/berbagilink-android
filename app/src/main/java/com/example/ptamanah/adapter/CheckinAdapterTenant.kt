package com.example.ptamanah.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ptamanah.data.response.DataItemtenant
import com.example.ptamanah.databinding.ListLogcheckinTenantBinding

class CheckinAdapterTenant :
    PagingDataAdapter<DataItemtenant, CheckinAdapterTenant.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListLogcheckinTenantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ListLogcheckinTenantBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemtenant) {
            binding.IdbookTV.text = data.ticketId
            binding.namaChekinTv.text = data.nama
            binding.namatiketChekinTv.text = data.email
            binding.notelpChekinTv.text = data.noTelp
            binding.tanggalcekinTv.text = data.visitedAt
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemtenant>() {
            override fun areItemsTheSame(oldItem: DataItemtenant, newItem: DataItemtenant): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItemtenant, newItem: DataItemtenant): Boolean {
                return oldItem == newItem
            }
        }
    }
}
