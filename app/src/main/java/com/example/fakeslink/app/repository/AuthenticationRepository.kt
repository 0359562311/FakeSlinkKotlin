package com.example.fakeslink.app.repository
import com.example.fakeslink.app.model.SessionService
import com.example.fakeslink.utils.NetworkUtils
import com.example.fakeslink.utils.Result
import kotlinx.coroutines.delay
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

class AuthenticationRepository {
    private val sessionService = NetworkUtils.buildService(SessionService::class.java)

    suspend fun login(username: String, password: String): Result{
        return try {
            val response = sessionService.login(mapOf("username" to username, "password" to password))
            return if(response.isSuccessful) {
                Result.Success(data = response.body()?.data!!)
            } else {
                val jsonObject = JSONObject(response.errorBody()?.string()?:"")
                Result.Failure(message = jsonObject.getJSONObject("data").getString("detail"))
            }
        } catch(e: SocketTimeoutException) {
            Result.Failure(message = "Timeout")
        }
        catch (e: IOException) {
            print(e.cause)
            Result.Failure(message = e.message?:"Unknown error")
        }
    }
}