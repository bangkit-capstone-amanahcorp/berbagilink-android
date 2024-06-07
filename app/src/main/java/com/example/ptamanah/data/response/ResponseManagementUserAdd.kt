package com.example.ptamanah.data.response

import com.google.gson.annotations.SerializedName

data class ResponseManagementUserAdd(

	@field:SerializedName("code")
	val code: String? = null,

	@field:SerializedName("data")
	val data: DataAdd? = null,

	@field:SerializedName("info")
	val info: String? = null
)

data class Errors(

	@field:SerializedName("password")
	val password: List<String>? = null,

	@field:SerializedName("role")
	val role: List<String>? = null,

	@field:SerializedName("name")
	val name: List<String>? = null,

	@field:SerializedName("email")
	val email: List<String>? = null
)

data class DataAdd(

	@field:SerializedName("errors")
	val errors: Errors
)