package com.example.ptamanah.data.retrofit

import com.example.ptamanah.data.response.ResponseListEvent
import com.example.ptamanah.data.response.ResponseLogin
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun userLogin(
        @Field("user") email: String,
        @Field("password") password: String
    ): ResponseLogin


    @GET("event-mobile/event?paginate=false")
    suspend fun getEvents(
        @Header("Authorization") token: String
    ) : ResponseListEvent
}