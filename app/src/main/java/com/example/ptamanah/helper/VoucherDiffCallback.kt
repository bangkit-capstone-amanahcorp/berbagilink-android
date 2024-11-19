package com.example.ptamanah.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.ptamanah.data.response.voucher.DataVoucher

class VoucherDiffCallback : DiffUtil.ItemCallback<DataVoucher>() {
    override fun areItemsTheSame(oldItem: DataVoucher, newItem: DataVoucher): Boolean {
        // Bandingkan ID atau properti unik dari DataVoucher
        return oldItem.id == newItem.id // Misalkan DataVoucher memiliki properti id
    }

    override fun areContentsTheSame(oldItem: DataVoucher, newItem: DataVoucher): Boolean {
        // Bandingkan konten item
        return oldItem == newItem
    }
}