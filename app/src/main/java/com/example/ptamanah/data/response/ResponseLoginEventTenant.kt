package com.example.ptamanah.data.response

import com.google.gson.annotations.SerializedName

data class ResponseLoginEventTenant(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: Dataa? = null,

	@field:SerializedName("info")
	val info: String? = null
)

data class Dataa(

	@field:SerializedName("token")
	val token: String? = null
)
