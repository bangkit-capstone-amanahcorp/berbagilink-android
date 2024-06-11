package com.example.ptamanah.data.response

import com.google.gson.annotations.SerializedName

data class ResponseReportStatistic(

	@field:SerializedName("manual_ticket")
	val manualTicket: ManualTicket? = null,

	@field:SerializedName("ticket")
	val ticket: TicketStatistic? = null
)

data class TicketsItemStatistic(

	@field:SerializedName("terjual")
	val terjual: Int? = null,

	@field:SerializedName("checkin")
	val checkin: String? = null,

	@field:SerializedName("nama_tiket")
	val namaTiket: String? = null,

	@field:SerializedName("failed")
	val failed: String? = null,

	@field:SerializedName("uncheck")
	val uncheck: String? = null
)

data class ManualTicket(

	@field:SerializedName("tickets")
	val tickets: List<Any?>? = null,

	@field:SerializedName("total_tiket")
	val totalTiket: Int? = null,

	@field:SerializedName("sudah_check_in")
	val sudahCheckIn: Int? = null,

	@field:SerializedName("failedCheckIn")
	val failedCheckIn: Int? = null,

	@field:SerializedName("belum_check_in")
	val belumCheckIn: Int? = null
)

data class TicketStatistic(

	@field:SerializedName("event_id")
	val eventId: String? = null,

	@field:SerializedName("tickets")
	val tickets: List<TicketsItemStatistic?>? = null,

	@field:SerializedName("total_tiket")
	val totalTiket: Int? = null,

	@field:SerializedName("sudah_check_in")
	val sudahCheckIn: Int? = null,

	@field:SerializedName("failedCheckIn")
	val failedCheckIn: Int? = null,

	@field:SerializedName("belum_check_in")
	val belumCheckIn: Int? = null
)
