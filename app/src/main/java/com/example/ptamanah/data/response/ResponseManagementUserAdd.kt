package com.example.ptamanah.data.response

import com.google.gson.annotations.SerializedName

data class ResponseManagementUserAdd(

	@field:SerializedName("code")
	val code: String,

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("info")
	val info: String
)

data class Errors(

	@field:SerializedName("email")
	val email: List<String>
)

data class DataManagementAdd(

	@field:SerializedName("errors")
	val errors: Errors
)
