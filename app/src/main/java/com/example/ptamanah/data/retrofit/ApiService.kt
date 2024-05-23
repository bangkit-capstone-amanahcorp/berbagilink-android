package com.example.ptamanah.data.retrofit

import com.example.ptamanah.data.response.ResponseCheckEmail
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

    @GET("event-mobile/tenant/all-visitor?search=&limit=10&page=1&sort=desc&event_id=f2415eed-55f3-47d0-ab7a-988585b37dc1")
    suspend fun getChekin(
        @Header("Authorization") token: String
    ) : ResponseDataVisitorTenant

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
}