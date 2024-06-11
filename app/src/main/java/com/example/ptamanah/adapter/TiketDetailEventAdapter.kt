package com.example.ptamanah.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ptamanah.data.response.TicketsItem
import com.example.ptamanah.databinding.ListTiketEventDetailBinding

class TiketDetailEventAdapter : ListAdapter<TicketsItem, TiketDetailEventAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ListTiketEventDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tiket = getItem(position)
        holder.binding.apply {
            titleidTv.text = tiket.namaTiket
            jumlahQtyTv.text = tiket.qty.toString()
            jumlahHargaTv.text = tiket.harga.toString()
            isiNamaTiketTv.text = tiket.namaTiket
            IsitanggalAwalTv.text = tiket.startDatetime
            isiTanggalAkhirTv.text = tiket.endDatetime
            jumlahTerjualTv.text = tiket.terjual.toString()
        }
    }

    class ViewHolder(val binding: ListTiketEventDetailBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TicketsItem>() {
            override fun areItemsTheSame(
                oldItem: TicketsItem,
                newItem: TicketsItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: TicketsItem,
                newItem: TicketsItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}