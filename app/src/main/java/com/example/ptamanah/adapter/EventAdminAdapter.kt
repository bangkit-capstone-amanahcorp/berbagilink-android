package com.example.ptamanah.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.getevent.adapter.EventAdapter
import com.example.ptamanah.R
import com.example.ptamanah.data.response.DataItem
import com.example.ptamanah.databinding.ListEventsAdminBinding

class EventAdminAdapter : ListAdapter<DataItem, EventAdminAdapter.ViewHolder>(DIFF_CALLBACK) {

    private lateinit var onItemClickCallBack: OnItemClickCallBack
    private lateinit var onDaftarClickCallBack: OnDaftarClickCallBack

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }
    fun setOnDaftarClickCallBack(onDaftarClickCallBack:OnDaftarClickCallBack) {
        this.onDaftarClickCallBack = onDaftarClickCallBack
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ListEventsAdminBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val  event = getItem(position)
        holder.binding.apply {
            namaTempat.text = event.namaTempat
            namaEvent.text = event.namaEvent
            alamat.text = event.alamat

            if (event.saleStatus == "end") {
                statusIV.setImageResource(R.drawable.status_border_end)
                /*scnaBtn.backgroundTintList = ContextCompat.getColorStateList(holder.itemView.context, R.color.disable)
                scnaBtn.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                scnaBtn.isEnabled = false*/
                tvStatus.text = "Selesai"
                tvStatus.setTextColor(
                    ContextCompat.getColor(
                        holder.itemView.context,
                        R.color.disable
                    )
                )
                DetailBtn.setOnClickListener {
                    onItemClickCallBack.onItemClicked(event)
                }
            } else {
                //scnaBtn.setBackgroundResource(R.drawable.btn_biru)
                tvStatus.text = "On-Going"
                DetailBtn.setOnClickListener {
                    onItemClickCallBack.onItemClicked(event)
                }
                /*scnaBtn.setOnClickListener {
                    onItemClickCallBack.onItemClicked(event)
                }*/
                /*daftarBtn.setOnClickListener {
                    onDaftarClickCallBack.onDaftarClicked(event)
                }*/
            }
        }
    }

    class ViewHolder(val binding: ListEventsAdminBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItem,
                newItem: DataItem
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickCallBack {
        fun onItemClicked(user: DataItem)
    }
    interface OnDaftarClickCallBack {
        fun onDaftarClicked(user: DataItem)
    }
}