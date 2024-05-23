package com.example.ptamanah.data.response

import com.google.gson.annotations.SerializedName

data class ResponseLogoutTenant(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: DataLogout? = null,

	@field:SerializedName("info")
	val info: String? = null
)

data class DataLogout(
	val any: Any? = null
)
