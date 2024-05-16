package com.example.ptamanah.data.response

import com.google.gson.annotations.SerializedName

data class ResponseDataVisitorTenant(

	@field:SerializedName("data")
	val datacheckin: Datacheckin,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Boolean
)

data class DataItemtenant(

	@field:SerializedName("tenant_id")
	val tenantId: Int,

	@field:SerializedName("event_id")
	val eventId: String,

	@field:SerializedName("instance_name")
	val instanceName: Any,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("visited_at")
	val visitedAt: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("no_telp")
	val noTelp: String,

	@field:SerializedName("ticket_id")
	val ticketId: String,

	@field:SerializedName("email")
	val email: String
)

data class Datacheckin(

	@field:SerializedName("first_page_url")
	val firstPageUrl: String,

	@field:SerializedName("path")
	val path: String,

	@field:SerializedName("per_page")
	val perPage: String,

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("data")
	val data: List<DataItemtenant>,

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
