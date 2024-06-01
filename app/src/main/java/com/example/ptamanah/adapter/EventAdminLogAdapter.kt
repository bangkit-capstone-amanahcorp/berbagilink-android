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
import com.example.ptamanah.data.response.DataItemAdmin
import com.example.ptamanah.data.response.DataItemtenant
import com.example.ptamanah.databinding.ListEventsAdminBinding
import com.example.ptamanah.databinding.ListItemEventAdminBinding
import com.example.ptamanah.databinding.ListLogcheckinTenantBinding

class EventAdminLogAdapter : PagingDataAdapter <DataItemAdmin, EventAdminLogAdapter.MyViewHolder>(DIFF_CALLBACK) {


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
        fun bind(data: DataItemAdmin) {
            binding.nomor.text = data.invoice
            binding.namaPemilikIsi.text = data.nama
            binding.namaTiketIsi.text = data.namaTiket
            binding.statusIsi.text = data.status


            binding.KodeBookingIsi.text = data.bookingCode
            binding.namaPemilikIsi2.text = data.nama
            binding.emailIsi.text = data.email
            binding.noHpIsi.text = data.handphone
            binding.waktuCheckIsi.text = data.checkinTime.toString()
            binding.jenisTiketIsi.text = data.namaTiket
            binding.kuotaIsi.text = data.kuota.toString()
            binding.statusIsi.text = data.status

        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemAdmin>() {
            override fun areItemsTheSame(oldItem: DataItemAdmin, newItem: DataItemAdmin): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItemAdmin, newItem: DataItemAdmin): Boolean {
                return oldItem == newItem
            }
        }
    }
}