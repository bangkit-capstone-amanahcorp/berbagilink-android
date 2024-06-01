package com.example.ptamanah.data.response

import com.google.gson.annotations.SerializedName

data class ResponseEventAdminLog(
	@field:SerializedName("code")
	val dataLog: DataLog,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("error")
	val error: Boolean


)

data class DataItemAdmin(

	@field:SerializedName("booking_code")
	val bookingCode: String,

	@field:SerializedName("checkin_time")
	val checkinTime: Any,

	@field:SerializedName("handphone")
	val handphone: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("nama_tiket")
	val namaTiket: String,

	@field:SerializedName("kuota")
	val kuota: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("invoice")
	val invoice: String,

	@field:SerializedName("manual")
	val manual: Int,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("slug")
	val slug: String,

	@field:SerializedName("status")
	val status: String
)

data class DataLog(

	@field:SerializedName("first_page_url")
	val firstPageUrl: String,

	@field:SerializedName("path")
	val path: String,

	@field:SerializedName("per_page")
	val perPage: Int,

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("data")
	val data: List<DataItemAdmin>,

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

