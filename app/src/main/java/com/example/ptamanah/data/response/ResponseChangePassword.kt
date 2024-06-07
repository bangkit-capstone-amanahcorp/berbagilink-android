package com.example.ptamanah.data.response

import com.google.gson.annotations.SerializedName

data class ResponseChangePassword(

	@field:SerializedName("data")
	val data: DataChangePassword? = null,

	@field:SerializedName("error")
	val error: Boolean? = null
)

data class DataChangePassword(
	val any: Any? = null
)
