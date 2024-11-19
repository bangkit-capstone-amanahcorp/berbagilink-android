package com.example.ptamanah.data.retrofit

import com.example.ptamanah.data.response.ResponseChangePassword
import com.example.ptamanah.data.response.ResponseCheckEmail
import com.example.ptamanah.data.response.ResponseCheckinCashier
import com.example.ptamanah.data.response.ResponseDataVisitorTenant
import com.example.ptamanah.data.response.ResponseDetailEvents
import com.example.ptamanah.data.response.ResponseEventAdminLog
import com.example.ptamanah.data.response.ResponseListEvent
import com.example.ptamanah.data.response.voucher.ResponseListVoucher
import com.example.ptamanah.data.response.ResponseLogin
import com.example.ptamanah.data.response.ResponseLoginEventTenant
import com.example.ptamanah.data.response.ResponseLogoutTenant
import com.example.ptamanah.data.response.ResponseManagementUser
import com.example.ptamanah.data.response.ResponseManagementUserAdd
import com.example.ptamanah.data.response.ResponseReportStatistic
import com.example.ptamanah.data.response.ResponseScan
import com.example.ptamanah.data.response.ResponseScanTenant
import com.example.ptamanah.data.response.ResponseTenantProfile
import com.example.ptamanah.data.response.ResponseTransactionEvents
import com.example.ptamanah.data.response.ResponseUpdateCheckIn
import com.example.ptamanah.data.response.ResponseUpdateUser
import com.example.ptamanah.data.response.Responsedelete
import com.example.ptamanah.data.response.voucher.ResponseVoucher
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun userLogin(
        @Field("user") email: String,
        @Field("password") password: String
    ): ResponseLogin

    @FormUrlEncoded
    @POST("event-mobile/tenant/check-email-registered")
    suspend fun checkEmail(
        @Field("email") email: String,
    ): ResponseCheckEmail

    @FormUrlEncoded
    @POST("event-mobile/tenant/login")
    suspend fun loginEvent(
        @Field("user") user: String,
        @Field("password") password: String,
        @Field("event_id") eventId: String,
    ): ResponseLoginEventTenant

    @POST("event-mobile/tenant/logout")
    suspend fun logoutTenant(
        @Header("Authorization") token: String
    ) : ResponseLogoutTenant


    @GET("event-mobile/event?paginate=false")
    suspend fun getEvents(
        @Header("Authorization") token: String
    ): ResponseListEvent

    @GET("event-mobile/event/{eventId}")
    suspend fun eventDetail(
        @Header("Authorization") token: String,
        @Path("eventId") eventId: String
    ): ResponseDetailEvents

    @GET("event-mobile/tenant/all-visitor")
    suspend fun getChekin(
        @Header("Authorization") token: String,
        @Query("search") search: String? = "",
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("sort") sort: String? = "desc",
        @Query("event_id") eventId: String
    ): ResponseDataVisitorTenant

    @GET("event-mobile/check-in/report/log/{eventId}")
    suspend fun getCheckinCashier(
        @Header("Authorization") token: String,
        @Path("eventId") eventId: String,
        @Query("page") page: Int,
        @Query("search") search: String? = ""
    ): ResponseCheckinCashier

    @GET("event-mobile/tenant/profile")
    suspend fun tenantProfile(
        @Header("Authorization") token: String
    ) : ResponseTenantProfile

    @FormUrlEncoded
    @POST("event-mobile/check-in/{eventId}/scan/update")
    suspend fun scanEvent(
        @Header("Authorization") token: String,
        @Path("eventId") eventId: String,
        @Field("booking_code") bookingCode: String
    ): ResponseScan

    @FormUrlEncoded
    @POST("event-mobile/tenant/event/{eventId}/scan-visitor")
    suspend fun scanTenant(
        @Header("Authorization") token: String,
        @Path("eventId") eventId: String,
        @Field("booking_code") bookingCode: String
    ): ResponseScanTenant


    @GET("event-mobile/check-in/{eventId}")
    suspend fun getEventAdmin(
    @Header("Authorization") token: String,
    @Path("eventId") eventId: String,
    @Query("page") page: Int,
    @Query("start_date") startDate: String,
    @Query( "end_date") endDate: String,
    @Query("keyword_value") keywordValue: String? = "",
    @Query("status") status: String,
    @Query("is_manual") isManual: Int? = null,
    ): ResponseEventAdminLog

    @POST("event-mobile/check-in/update/{id}")
    suspend fun updateCheckin(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ):ResponseUpdateCheckIn

    @GET("berbagi_link/user_management")
    suspend fun getManageUser(
        @Header("Authorization") token: String
    ): ResponseManagementUser

    @DELETE("berbagi_link/user_management/delete/{id}")
    suspend fun deleteUser(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Responsedelete

    @FormUrlEncoded
    @POST("berbagi_link/user_management/store")
    suspend fun addUser(
        @Header("Authorization") token: String,
        @Field("name") name: String,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String,
        @Field("email") email: String,
        @Field("role") role: String,
    ):ResponseManagementUserAdd
    @FormUrlEncoded
    @POST("berbagi_link/user_management/update/{id}")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("role") role: String
    ) : ResponseUpdateUser

    @FormUrlEncoded
    @POST("berbagi_link/user_management/change_password/{id}")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Field("password") password: String,
        @Field("password_confirmation") password_confirmation: String
    ) : ResponseChangePassword

    @GET("event-mobile/check-in/report/{eventId}")
    suspend fun getReportStatistic(
        @Header("Authorization") token: String,
        @Path("eventId") eventId: String
    ) : ResponseReportStatistic

    @GET("event-mobile/transaction/{eventId}")
    suspend fun getTransaction(
        @Header("Authorization") token: String,
        @Path("eventId") eventId: String,
        @Query("page") page: Int,
        @Query("search") search: String? = "",
        @Query("status") status: String?= ""
    ) : ResponseTransactionEvents

    @GET("event-mobile/event/{eventId}")
    suspend fun getTiketEventDetail(
        @Header("Authorization") token: String,
        @Path("eventId") eventId: String
    ) : ResponseDetailEvents


    @GET("marketplace/voucher")
    suspend fun getVoucher(
        @Header("Authorization") token: String
    ): ResponseListVoucher

    @FormUrlEncoded
    @POST("marketplace/voucher")
    suspend fun addVoucher(
        @Header("Authorization") token: String,
        @Field("voucher_name") name: String,
        @Field("voucher_code") code: String,
        @Field("voucher_type") type: String,
        @Field("feature_type") feature: String,
        @Field("usage_type") usage: String,
        @Field("voucher_value") value: Float,
        @Field("min_value") minValue: Int,
        @Field("max_value") maxValue: Int,
        @Field("quota") quota: Int,
        @Field("used") used: Int,
        @Field("description") description: String,
        @Field("is_active") isActive: Boolean
    ): ResponseVoucher
}