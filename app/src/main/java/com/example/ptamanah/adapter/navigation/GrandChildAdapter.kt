package com.example.ptamanah.adapter.navigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ptamanah.R

class GrandChildAdapter(
    private val grandChildList: List<String>
) : RecyclerView.Adapter<GrandChildAdapter.GrandChildViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GrandChildViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_nav_sub_child, parent, false)
        return GrandChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: GrandChildViewHolder, position: Int) {
        holder.bind(grandChildList[position])
    }

    override fun getItemCount(): Int {
        return grandChildList.size
    }

    class GrandChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val subChildTextView: TextView = itemView.findViewById(R.id.sub_child_title)

        fun bind(grandChildText: String) {
            subChildTextView.text = grandChildText
        }
    }
}
