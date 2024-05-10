package com.bangkit.getevent.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ptamanah.data.response.DataItem
import com.example.ptamanah.databinding.ListItemEventBinding


class EventAdapter : ListAdapter<DataItem, EventAdapter.ViewHolder>(DIFF_CALLBACK) {

    /*private lateinit var onItemClickCallBack: OnItemClickCallBack

    fun setOnItemClickCallBack(onItemClickCallBack: OnItemClickCallBack) {
        this.onItemClickCallBack = onItemClickCallBack
    }*/

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

        }

        /*holder.itemView.setOnClickListener {
            onItemClickCallBack.onItemClicked(event)
        }*/
    }

    class ViewHolder(val binding: ListItemEventBinding) : RecyclerView.ViewHolder(binding.root)

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

    /*interface OnItemClickCallBack {
        fun onItemClicked(user: DataItem)
    }*/
}