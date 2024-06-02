package com.example.ptamanah.data.response

import com.google.gson.annotations.SerializedName

data class ResponseUpdateCheckIn(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)

data class DataCheck(
	val any: Any? = null
)
