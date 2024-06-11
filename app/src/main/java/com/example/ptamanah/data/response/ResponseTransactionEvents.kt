package com.example.ptamanah.data.response

import com.google.gson.annotations.SerializedName

data class ResponseTransactionEvents(

    @field:SerializedName("data")
    val dataTransaction: DataTransaction,

    @field:SerializedName("error")
    val error: Boolean
)

data class DataItemTransaction(

    @field:SerializedName("admin_berbagi_link")
    val adminBerbagiLink: Int,

    @field:SerializedName("tanggal_transfer")
    val tanggalTransfer: Any,

    @field:SerializedName("amademy_pass")
    val amademyPass: Any,

    @field:SerializedName("mobile_banking")
    val mobileBanking: Any,

    @field:SerializedName("discount")
    val discount: Int,

    @field:SerializedName("internet_banking")
    val internetBanking: Any,

    @field:SerializedName("message_info_wa")
    val messageInfoWa: Any,

    @field:SerializedName("is_read")
    val isRead: Int,

    @field:SerializedName("nama_rekening")
    val namaRekening: Any,

    @field:SerializedName("qr_path")
    val qrPath: Any,

    @field:SerializedName("status_pembayaran")
    val statusPembayaran: String,

    @field:SerializedName("qr_url")
    val qrUrl: Any,

    @field:SerializedName("bukti_transfer_path")
    val buktiTransferPath: Any,

    @field:SerializedName("bukti_transfer_url")
    val buktiTransferUrl: Any,

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("atm")
    val atm: Any,

    @field:SerializedName("payment_expired_date")
    val paymentExpiredDate: String,

    @field:SerializedName("invoice_number")
    val invoiceNumber: String,

    @field:SerializedName("admin_channel")
    val adminChannel: Int,

    @field:SerializedName("total_harga_tiket")
    val totalHargaTiket: Int,

    @field:SerializedName("invoice_prefix")
    val invoicePrefix: String,

    @field:SerializedName("gerai")
    val gerai: Any,

    @field:SerializedName("is_notify_success")
    val isNotifySuccess: Int,

    @field:SerializedName("message_type")
    val messageType: String,

    @field:SerializedName("created_by")
    val createdBy: Any,

    @field:SerializedName("bank_image_url")
    val bankImageUrl: Any,

    @field:SerializedName("send_time")
    val sendTime: Any,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("is_certificate_send")
    val isCertificateSend: Int,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("message_info")
    val messageInfo: Any,

    @field:SerializedName("jumlah_transfer")
    val jumlahTransfer: Any,

    @field:SerializedName("biller_transaction_id")
    val billerTransactionId: Any,

    @field:SerializedName("edited_by")
    val editedBy: Any,

    @field:SerializedName("admin_fee")
    val adminFee: Int,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("is_notify_expired")
    val isNotifyExpired: Int,

    @field:SerializedName("user_bank_id")
    val userBankId: Any,

    @field:SerializedName("total")
    val total: Int,

    @field:SerializedName("handphone")
    val handphone: String,

    @field:SerializedName("updated_at")
    val updatedAt: String,

    @field:SerializedName("nama_bank")
    val namaBank: Any,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("kode_unik")
    val kodeUnik: Int,

    @field:SerializedName("is_notify_invoice")
    val isNotifyInvoice: Int,

    @field:SerializedName("nomor_rekening")
    val nomorRekening: Any,

    @field:SerializedName("catatan")
    val catatan: Any,

    @field:SerializedName("check_status")
    val checkStatus: Int,

    @field:SerializedName("token")
    val token: String,

    @field:SerializedName("event_id")
    val eventId: String,

    @field:SerializedName("voucher_id")
    val voucherId: Any,

    @field:SerializedName("donate_amount")
    val donateAmount: Int,

    @field:SerializedName("invoice")
    val invoice: String,

    @field:SerializedName("customer_id")
    val customerId: Any,

    @field:SerializedName("status_certificate")
    val statusCertificate: String
)

data class DataTransaction(

    @field:SerializedName("first_page_url")
    val firstPageUrl: String,

    @field:SerializedName("path")
    val path: String,

    @field:SerializedName("per_page")
    val perPage: Int,

    @field:SerializedName("total")
    val total: Int,

    @field:SerializedName("data")
    val data: List<DataItemTransaction>,

    @field:SerializedName("last_page")
    val lastPage: Int,

    @field:SerializedName("last_page_url")
    val lastPageUrl: String,

    @field:SerializedName("next_page_url")
    val nextPageUrl: Any,

    @field:SerializedName("from")
    val from: Int,

    @field:SerializedName("to")
    val to: Int,

    @field:SerializedName("prev_page_url")
    val prevPageUrl: Any,

    @field:SerializedName("current_page")
    val currentPage: Int
)
