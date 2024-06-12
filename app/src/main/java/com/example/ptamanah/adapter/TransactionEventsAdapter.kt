package com.example.ptamanah.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ptamanah.R
import com.example.ptamanah.data.response.DataItemTransaction
import com.example.ptamanah.databinding.ListItemTransaksiBinding

class TransactionEventsAdapter:
    PagingDataAdapter<DataItemTransaction, TransactionEventsAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var onDetailClickCallBack: OnDetailClickCallBack? = null
    private var namaEvent: String? = null
    private var username: String? = null

    fun setOnDetailClickCallBack(onDetailClickCallBack: OnDetailClickCallBack) {
        this.onDetailClickCallBack = onDetailClickCallBack
    }

    fun setNamaEvent(namaEvent: String?) {
        this.namaEvent = namaEvent
    }

    fun setUsername(username: String?) {
        this.username = username
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ListItemTransaksiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data, onDetailClickCallBack, namaEvent, username)
        }
    }

    class MyViewHolder(private val binding: ListItemTransaksiBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            data: DataItemTransaction,
            onDetailClickCallBack: OnDetailClickCallBack?,
            namaEvent: String?,
            username: String?
        ) {
            binding.invoiceTv.text = data.invoice
            binding.userIdTv.text = data.userId.toString()
            binding.namaEventTransactionTv.text = namaEvent
            binding.paymentExpiredDateTv.text = data.paymentExpiredDate
            binding.namaPenggunaTv.text = data.nama
            binding.emailTv.text = data.email
            binding.NoHpTv.text = data.handphone
            binding.totalTv.text = "Rp.  ${data.totalHargaTiket}"


            when (data.statusPembayaran) {
                "UNPAID" -> {
                    binding.statusIV.setImageResource(R.drawable.roll_cashier_border)
                    binding.tvStatus.text = "Unpaid"
                    binding.tvStatus.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.ungu
                        )
                    )
                }
                "CONFIRMED" -> {
                    binding.statusIV.setImageResource(R.drawable.status_confirmed)
                    binding.tvStatus.text = "Confirmed"
                    binding.tvStatus.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.biru_toska
                        )
                    )
                }
                "FAILED" -> {
                    binding.statusIV.setImageResource(R.drawable.status_failed)
                    binding.tvStatus.text = "Failed"
                    binding.tvStatus.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.failed
                        )
                    )
                }
                else -> {
                    binding.statusIV.setImageResource(R.drawable.roll_admin_border)
                    binding.tvStatus.text = "Paid"
                    binding.tvStatus.setTextColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.green_berhasil
                        )
                    )
                }
            }

            itemView.setOnClickListener {
                if (binding.showbutton.visibility == View.VISIBLE) {
                    binding.showbutton.visibility = View.GONE
                    binding.showbutton0.visibility = View.GONE
                    binding.cardView.strokeColor = itemView.context.getColor(R.color.abu_terang)
                } else {
                    binding.showbutton.visibility = View.VISIBLE
                    binding.showbutton0.visibility = View.VISIBLE
                    binding.cardView.strokeColor = itemView.context.getColor(R.color.biru_toska)
                }
            }

            binding.btnDetailTransaksi.setOnClickListener {
                val context = itemView.context
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://berbagi.link/$username/event/transaction-detail/${data.id}/${data.token}")
                )
                context.startActivity(intent)
            }
        }
    }
    interface OnDetailClickCallBack {
        fun onDetailClicked(user: DataItemTransaction)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemTransaction>() {
            override fun areItemsTheSame(oldItem: DataItemTransaction, newItem: DataItemTransaction): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItemTransaction, newItem: DataItemTransaction): Boolean {
                return oldItem == newItem
            }
        }
    }
}
