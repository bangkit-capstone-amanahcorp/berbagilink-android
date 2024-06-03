package com.bangkit.getevent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ptamanah.R
import com.example.ptamanah.data.response.DataItem
import com.example.ptamanah.databinding.ListItemEventBinding

class EventAdapter : ListAdapter<DataItem, EventAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onDaftarClickCallBack: OnDaftarClickCallBack
    private lateinit var onScanClickCallBack: OnScanClickCallBack

    fun setOnDaftarClickCallBack(onDaftarClickCallBack: OnDaftarClickCallBack) {
        this.onDaftarClickCallBack = onDaftarClickCallBack
    }

    fun setOnScanClickCallBack(onScanClickCallBack: OnScanClickCallBack) {
        this.onScanClickCallBack = onScanClickCallBack
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = getItem(position)
        holder.binding.apply {
            namaTempat.text = event.namaTempat
            namaEvent.text = event.namaEvent
            alamat.text = event.alamat

            if (event.saleStatus == "end") {
                statusIV.setImageResource(R.drawable.status_border_end)
                scnaBtn.backgroundTintList = ContextCompat.getColorStateList(holder.itemView.context, R.color.disable)
                scnaBtn.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                scnaBtn.isEnabled = false
                tvStatus.text = "Selesai"
                tvStatus.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.disable
                    )
                )
                daftarBtn.setOnClickListener {
                    onDaftarClickCallBack.onDaftarClicked(event)
                }
            } else {
                scnaBtn.setBackgroundResource(R.drawable.btn_biru)
                tvStatus.text = "On-Going"
                scnaBtn.setOnClickListener {
                    onScanClickCallBack.onScanClicked(event)
                }
                daftarBtn.setOnClickListener {
                    onDaftarClickCallBack.onDaftarClicked(event)
                }
            }
        }
    }

    class ViewHolder(val binding: ListItemEventBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnDaftarClickCallBack {
        fun onDaftarClicked(user: DataItem)
    }

    interface OnScanClickCallBack {
        fun onScanClicked(user: DataItem)
    }
}
