package com.example.ptamanah.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ptamanah.data.response.DataItemCashier
import com.example.ptamanah.databinding.ListLogcheckinCashierBinding

class CheckinAdapterChasier :
    PagingDataAdapter<DataItemCashier, CheckinAdapterChasier.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListLogcheckinCashierBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ListLogcheckinCashierBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemCashier) {
            binding.IdbookTvCashier.text = data.ticketId
            binding.namaChekinTvCashier.text = data.ticket.transaction.nama
            binding.kodebookChekinTvCashier.text = data.ticket.bookingCode
            binding.namatiketChekinTvCashier.text = data.ticketType
            binding.tanggalcekinTv.text = data.updatedAt
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemCashier>() {
            override fun areItemsTheSame(oldItem: DataItemCashier, newItem: DataItemCashier): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItemCashier, newItem: DataItemCashier): Boolean {
                return oldItem == newItem
            }
        }
    }
}
