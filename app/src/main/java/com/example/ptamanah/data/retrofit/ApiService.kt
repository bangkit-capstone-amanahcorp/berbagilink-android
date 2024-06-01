package com.example.ptamanah.data.retrofit

import com.example.ptamanah.data.response.AdminEventLogCheckResponse
import com.example.ptamanah.data.response.ResponseCheckEmail
import com.example.ptamanah.data.response.ResponseCheckinCashier
import com.example.ptamanah.data.response.ResponseDataVisitorTenant
import com.example.ptamanah.data.response.ResponseListEvent
import com.example.ptamanah.data.response.ResponseLogin
import com.example.ptamanah.data.response.ResponseLoginEventTenant
import com.example.ptamanah.data.response.ResponseLogoutTenant
import com.example.ptamanah.data.response.ResponseScan
import com.example.ptamanah.data.response.ResponseScanTenant
import com.example.ptamanah.data.response.ResponseTenantProfile
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

    //Checkin Tenant
    @GET("event-mobile/tenant/all-visitor")
    suspend fun getChekin(
        @Header("Authorization") token: String,
        @Query("search") search: String? = "",
        @Query("limit") limit: Int,
        @Query("page") page: Int,
        @Query("sort") sort: String? = "desc",
        @Query("event_id") eventId: String
    ): ResponseDataVisitorTenant
    //Checkin Cashier
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

    @FormUrlEncoded
    @GET("event-mobile/check-in/{eventId}")
    suspend fun getEventAdmin(
    @Header("Authorization") token: String,
    @Path("eventId") eventId: String,
    @Field("status") status: String,
    @Field("search") search: String? = "",
    @Field("page") page: Int
    ): AdminEventLogCheckResponse
}