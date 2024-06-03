package com.example.ptamanah.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ptamanah.data.response.DataCheckEmail
import com.example.ptamanah.databinding.ListEventTenantBinding
import com.example.ptamanah.view.eventTenant.BottomLogin

class EventListTenantAdapter() : ListAdapter<DataCheckEmail, EventListTenantAdapter.ViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ListEventTenantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val event = getItem(position)
        holder.binding.apply {
            namaEvent.text = event.namaEvent
            namaOrganizer.text = event.namaOrganizer
            Glide.with(holder.itemView.context)
                .load(event.imageCoverUrl)
                .into(imgItem)

            cardView.setOnClickListener{
                val bottomShet = BottomLogin.newInstance(event.id.toString())
                bottomShet.show((holder.itemView.context as AppCompatActivity).supportFragmentManager, BottomLogin.TAG)
            }
        }
    }

    class ViewHolder(val binding: ListEventTenantBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataCheckEmail>() {
            override fun areItemsTheSame(
                oldItem: DataCheckEmail,
                newItem: DataCheckEmail
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataCheckEmail,
                newItem: DataCheckEmail
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}