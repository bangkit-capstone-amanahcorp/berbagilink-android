package com.example.ptamanah.data.response

import com.google.gson.annotations.SerializedName

data class ResponseListEvent(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("data")
	val data: List<DataItem>? = null,

)

data class Cat(

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("color")
	val color: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class DataItem(

	@field:SerializedName("waktu_schedule_start")
	val waktuScheduleStart: String? = null,

	@field:SerializedName("start_datetime")
	val startDatetime: String? = null,

	@field:SerializedName("end_datetime")
	val endDatetime: String? = null,

	@field:SerializedName("is_published")
	val isPublished: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("min_ticket_per_transaction")
	val minTicketPerTransaction: Int? = null,

	@field:SerializedName("nama_tempat")
	val namaTempat: String? = null,

	@field:SerializedName("image_cover_url")
	val imageCoverUrl: String? = null,

	@field:SerializedName("logo_organizer_url")
	val logoOrganizerUrl: String? = null,

	@field:SerializedName("certificate_url")
	val certificateUrl: Any? = null,

	@field:SerializedName("terms")
	val terms: Any? = null,

	@field:SerializedName("active_certificate")
	val activeCertificate: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("nama_event")
	val namaEvent: String? = null,

	@field:SerializedName("lat")
	val lat: Any? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("schedule_start")
	val scheduleStart: Any? = null,

	@field:SerializedName("nama_organizer")
	val namaOrganizer: String? = null,

	@field:SerializedName("waktu_start")
	val waktuStart: String? = null,

	@field:SerializedName("lng")
	val lng: Any? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: Any? = null,

	@field:SerializedName("tanggal_start")
	val tanggalStart: String? = null,

	@field:SerializedName("alamat")
	val alamat: String? = null,

	@field:SerializedName("image_cover_path")
	val imageCoverPath: String? = null,

	@field:SerializedName("user_id")
	val userId: Int? = null,

	@field:SerializedName("active_catatan")
	val activeCatatan: Int? = null,

	@field:SerializedName("youtube_url")
	val youtubeUrl: Any? = null,

	@field:SerializedName("only_once_per_email")
	val onlyOncePerEmail: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("ask_organizer")
	val askOrganizer: Int? = null,

	@field:SerializedName("logo_organizer_path")
	val logoOrganizerPath: String? = null,

	@field:SerializedName("survei_link")
	val surveiLink: Any? = null,

	@field:SerializedName("certificate_path")
	val certificatePath: Any? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("map_link")
	val mapLink: Any? = null,

	@field:SerializedName("waktu_end")
	val waktuEnd: String? = null,

	@field:SerializedName("cat")
	val cat: Cat? = null,

	@field:SerializedName("custom_expired_date")
	val customExpiredDate: String? = null,

	@field:SerializedName("tanggal_schedule_end")
	val tanggalScheduleEnd: String? = null,

	@field:SerializedName("tanggal_schedule_start")
	val tanggalScheduleStart: String? = null,

	@field:SerializedName("kota")
	val kota: Any? = null,

	@field:SerializedName("max_ticket_per_transaction")
	val maxTicketPerTransaction: Int? = null,

	@field:SerializedName("streaming_url")
	val streamingUrl: Any? = null,

	@field:SerializedName("active_voucher")
	val activeVoucher: Int? = null,

	@field:SerializedName("waktu_schedule_end")
	val waktuScheduleEnd: String? = null,

	@field:SerializedName("sale_status")
	val saleStatus: String? = null,

	@field:SerializedName("is_survei")
	val isSurvei: Int? = null,

	@field:SerializedName("tanggal_end")
	val tanggalEnd: String? = null,

	@field:SerializedName("schedule_end")
	val scheduleEnd: Any? = null,

	@field:SerializedName("category")
	val category: String? = null
)
