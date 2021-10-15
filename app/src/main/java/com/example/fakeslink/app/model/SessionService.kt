package com.example.fakeslink.app.model

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface SessionService {
    @FormUrlEncoded
    @POST("auth/jwt/create/")
    suspend fun login(@FieldMap body: Map<String,String>): Response<ResponseObject<Session>>

    @FormUrlEncoded
    @POST("auth/jwt/refresh")
    fun refreshToken(@Field("refresh") refresh: String): Call<ResponseObject<Session>>
}