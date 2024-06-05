package com.example.ptamanah.data.response

import com.google.gson.annotations.SerializedName

data class ResponseManagementUser(

    @field:SerializedName("data")
    val datamanagement: List<DataItemManagementUser>,

    @field:SerializedName("error")
    val error: Boolean
)

data class DataItemManagementUser(

    @field:SerializedName("role")
    val role: String,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("created_at_unix")
    val createdAtUnix: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("email")
    val email: String
)
data class Responsedelete(

    @field:SerializedName("data")
    val datadelete: Datadelete,

    @field:SerializedName("error")
    val error: Boolean
)

data class Datadelete(
    val any: Any? = null
)
