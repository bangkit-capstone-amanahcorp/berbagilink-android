package com.example.ptamanah.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ptamanah.R
import com.example.ptamanah.data.response.DataItem
import com.example.ptamanah.data.response.DataItemLog
import com.example.ptamanah.data.response.DataItemtenant
import com.example.ptamanah.databinding.ListEventsAdminBinding
import com.example.ptamanah.databinding.ListItemEventAdminBinding
import com.example.ptamanah.databinding.ListLogcheckinTenantBinding

class EventAdminLogAdapter : PagingDataAdapter <DataItemLog, EventAdminLogAdapter.MyViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemEventAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            Log.d("adapterrrrsss", "Binding data at position $position: $data")
            holder.bind(data)
        }
    }

    class MyViewHolder(private val binding: ListItemEventAdminBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemLog) {
            binding.nomor.text = data.invoice
            binding.namaPemilikIsi.text = data.nama
            binding.namaTiketIsi.text = data.namaTiket
            binding.statusIsi.text = data.status


            binding.KodeBookingIsi.text = data.bookingCode
            binding.namaPemilikIsi2.text = data.nama
            binding.emailIsi.text = data.email
            binding.jenisTiketIsi.text = data.namaTiket
            binding.statusIsi.text = data.status

        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemLog>() {
            override fun areItemsTheSame(oldItem: DataItemLog, newItem: DataItemLog): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItemLog, newItem: DataItemLog): Boolean {
                return oldItem == newItem
            }
        }
    }
}