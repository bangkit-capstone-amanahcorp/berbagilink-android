package com.example.ptamanah.data.response

import com.google.gson.annotations.SerializedName

data class ResponseUpdateUser(

	@field:SerializedName("data")
	val data: DataUpdate? = null,

	@field:SerializedName("error")
	val error: Boolean? = null
)

data class DataUpdate(

	@field:SerializedName("is_new_feature")
	val isNewFeature: Int? = null,

	@field:SerializedName("is_block")
	val isBlock: Int? = null,

	@field:SerializedName("language")
	val language: Any? = null,

	@field:SerializedName("key_static")
	val keyStatic: Any? = null,

	@field:SerializedName("profile_image")
	val profileImage: String? = null,

	@field:SerializedName("table_page_analytic")
	val tablePageAnalytic: Int? = null,

	@field:SerializedName("verified_at")
	val verifiedAt: String? = null,

	@field:SerializedName("connected_link_id")
	val connectedLinkId: Any? = null,

	@field:SerializedName("active_instant_payment")
	val activeInstantPayment: Int? = null,

	@field:SerializedName("passport_image")
	val passportImage: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("ktp_number")
	val ktpNumber: Any? = null,

	@field:SerializedName("widget_key_bisatopup")
	val widgetKeyBisatopup: Any? = null,

	@field:SerializedName("address")
	val address: Any? = null,

	@field:SerializedName("is_verify_travel")
	val isVerifyTravel: Int? = null,

	@field:SerializedName("last_login")
	val lastLogin: Any? = null,

	@field:SerializedName("affiliation_code")
	val affiliationCode: Any? = null,

	@field:SerializedName("deleted_at")
	val deletedAt: Any? = null,

	@field:SerializedName("table_analytic")
	val tableAnalytic: Int? = null,

	@field:SerializedName("ktp_image")
	val ktpImage: String? = null,

	@field:SerializedName("is_fundraiser")
	val isFundraiser: Int? = null,

	@field:SerializedName("api_key")
	val apiKey: Any? = null,

	@field:SerializedName("affiliation_user_id")
	val affiliationUserId: Any? = null,

	@field:SerializedName("parent_id")
	val parentId: Int? = null,

	@field:SerializedName("passport_number")
	val passportNumber: Any? = null,

	@field:SerializedName("is_active_support")
	val isActiveSupport: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("point_bisatopup")
	val pointBisatopup: Int? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: Any? = null,

	@field:SerializedName("username")
	val username: Any? = null
)
