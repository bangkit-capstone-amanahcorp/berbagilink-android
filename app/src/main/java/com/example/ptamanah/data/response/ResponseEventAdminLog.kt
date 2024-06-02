package com.example.ptamanah.data.response

import com.google.gson.annotations.SerializedName

data class ResponseEventAdminLog(

	@field:SerializedName("data")
	val data: DataLog? = null,

	@field:SerializedName("error")
	val error: Boolean? = null
)

data class DataItemAdmin(

	@field:SerializedName("booking_code")
	val bookingCode: String? = null,

	@field:SerializedName("checkin_time")
	val checkinTime: String? = null,

	@field:SerializedName("handphone")
	val handphone: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("nama_tiket")
	val namaTiket: String? = null,

	@field:SerializedName("kuota")
	val kuota: Int? = null,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("invoice")
	val invoice: String? = null,

	@field:SerializedName("manual")
	val manual: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("status")
	var status: String
)

data class DataLog(

	@field:SerializedName("first_page_url")
	val firstPageUrl: String? = null,

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("per_page")
	val perPage: Int? = null,

	@field:SerializedName("total")
	val total: Int? = null,

	@field:SerializedName("data")
	val data: List<DataItemAdmin?>? = null,

	@field:SerializedName("last_page")
	val lastPage: Int? = null,

	@field:SerializedName("last_page_url")
	val lastPageUrl: String? = null,

	@field:SerializedName("next_page_url")
	val nextPageUrl: Any? = null,

	@field:SerializedName("from")
	val from: Int? = null,

	@field:SerializedName("to")
	val to: Int? = null,

	@field:SerializedName("prev_page_url")
	val prevPageUrl: Any? = null,

	@field:SerializedName("current_page")
	val currentPage: Int? = null
)
