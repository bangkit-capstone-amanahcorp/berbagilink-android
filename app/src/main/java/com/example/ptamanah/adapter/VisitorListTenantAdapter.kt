package com.example.ptamanah.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ptamanah.R
import com.example.ptamanah.data.response.DataItemtenant

class CheckinAdapter(private val data: List<DataItemtenant>) :
    RecyclerView.Adapter<CheckinAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_logcheckin_tenant, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(dataItemTenant: DataItemtenant) {
            itemView.findViewById<TextView>(R.id.IdbookTV).text = dataItemTenant.ticketId
            itemView.findViewById<TextView>(R.id.nama_chekin_tv).text = dataItemTenant.nama
            itemView.findViewById<TextView>(R.id.namatiket_chekin_tv).text = dataItemTenant.email
            itemView.findViewById<TextView>(R.id.kodebook_chekin_tv).text = dataItemTenant.noTelp
            itemView.findViewById<TextView>(R.id.tanggal_chekin_tv).text = dataItemTenant.visitedAt
        }
    }
}
