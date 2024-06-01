package com.example.ptamanah.data.response

import com.google.gson.annotations.SerializedName

data class AdminEventLogCheckResponse(
	@field:SerializedName("code")
	val dataCheckinLogAdmin: DataCheckinLogAdmin,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("error")
	val error: Boolean
)

data class DataItemLog(

	@field:SerializedName("booking_code")
	val bookingCode: String,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("nama_tiket")
	val namaTiket: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("invoice")
	val invoice: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("status")
	val status: String
)

data class DataAd(

	@field:SerializedName("first_page_url")
	val firstPageUrl: String,

	@field:SerializedName("path")
	val path: String,

	@field:SerializedName("per_page")
	val perPage: Int,

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("data")
	val data: List<DataItemLog>,

	@field:SerializedName("last_page")
	val lastPage: Int,

	@field:SerializedName("last_page_url")
	val lastPageUrl: String,

	@field:SerializedName("next_page_url")
	val nextPageUrl: String,

	@field:SerializedName("from")
	val from: Int,

	@field:SerializedName("to")
	val to: Int,

	@field:SerializedName("prev_page_url")
	val prevPageUrl: Any,

	@field:SerializedName("current_page")
	val currentPage: Int
)

data class DataCheckinLogAdmin(
	@field:SerializedName("data_ad")
	val dataAd: DataAd,
)
