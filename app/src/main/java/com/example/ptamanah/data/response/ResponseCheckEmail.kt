package com.example.ptamanah.data.response

import com.google.gson.annotations.SerializedName

data class ResponseCheckEmail(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: List<DataCheckEmail?>? = null,

	@field:SerializedName("info")
	val info: String? = null
)

data class DataCheckEmail(

	@field:SerializedName("nama_organizer")
	val namaOrganizer: String? = null,

	@field:SerializedName("waktu_start")
	val waktuStart: String? = null,

	@field:SerializedName("waktu_schedule_start")
	val waktuScheduleStart: String? = null,

	@field:SerializedName("tanggal_schedule_start")
	val tanggalScheduleStart: String? = null,

	@field:SerializedName("start_datetime")
	val startDatetime: String? = null,

	@field:SerializedName("end_datetime")
	val endDatetime: String? = null,

	@field:SerializedName("waktu_schedule_end")
	val waktuScheduleEnd: String? = null,

	@field:SerializedName("sale_status")
	val saleStatus: String? = null,

	@field:SerializedName("tanggal_start")
	val tanggalStart: String? = null,

	@field:SerializedName("image_cover_url")
	val imageCoverUrl: String? = null,

	@field:SerializedName("logo_organizer_url")
	val logoOrganizerUrl: String? = null,

	@field:SerializedName("tanggal_end")
	val tanggalEnd: String? = null,

	@field:SerializedName("waktu_end")
	val waktuEnd: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("nama_event")
	val namaEvent: String? = null,

	@field:SerializedName("tanggal_schedule_end")
	val tanggalScheduleEnd: String? = null
)
