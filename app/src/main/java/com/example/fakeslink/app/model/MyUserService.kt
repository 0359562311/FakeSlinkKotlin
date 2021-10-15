package com.example.fakeslink.app.model

import retrofit2.Response
import retrofit2.http.GET

interface MyUserService {
    @GET("user/me")
    suspend fun getMyProfile(): Response<ResponseObject<Student>>
}