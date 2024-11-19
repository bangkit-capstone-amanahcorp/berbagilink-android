package com.example.ptamanah.data.response.voucher

import com.google.gson.annotations.SerializedName

data class ResponseVoucher(
    @field:SerializedName("status")
    val status: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("voucher")
    val voucher: Voucher? = null,
)

data class Voucher(

    @field:SerializedName("voucher_name")
    val voucher_name: String? = null,

    @field:SerializedName("voucher_code")
    val voucher_code: String? = null,

    @field:SerializedName("voucher_type")
    val voucher_type: String? = null,

    @field:SerializedName("feature_type")
    val feature_type: String? = null,

    @field:SerializedName("usage_type")
    val usage_type: String? = null,

    @field:SerializedName("voucher_value")
    val voucher_value: Float? = null,

    @field:SerializedName("min_value")
    val min_value: Int? = null,

    @field:SerializedName("max_value")
    val max_value: Int? = null,

    @field:SerializedName("quota")
    val quota: Int? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("is_active")
    val is_active: Boolean? = null,

    @field:SerializedName("updated_at")
    val updated_at: String? = null,

    @field:SerializedName("created_at")
    val created_at: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,
)