package com.example.fakeslink.app.repository

import com.example.fakeslink.utils.NetworkUtils
import com.example.fakeslink.utils.Result
import com.example.fakeslink.app.model.MyUserService
import org.json.JSONObject
import java.io.IOException

class UserRepository {
    private val myUserService = NetworkUtils.buildService(MyUserService::class.java)

    suspend fun getMyProfile() : Result {
        return try {
            val response = myUserService.getMyProfile()
            return if(response.isSuccessful) {
                Result.Success(data = response.body()?.data!!)
            } else {
                val jsonObject = JSONObject(response.errorBody()?.string()?:"")
                Result.Failure(message = jsonObject.getJSONObject("data").getString("detail"))
            }
        } catch (e: IOException) {
            Result.Failure(message = e.message?:"Unknown error")
        }
    }
}