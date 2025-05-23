package com.uttkarsh.InstaStudio.utils.api

import com.google.gson.Gson
import com.uttkarsh.InstaStudio.domain.model.ApiResponse
import retrofit2.HttpException
import java.io.IOException

object ApiErrorExtractor {

    fun extractMessage(exception: HttpException): String {
        return try {
            val errorBody = exception.response()?.errorBody()?.string()
            if (errorBody != null) {
                val gson = Gson()
                val apiResponse = gson.fromJson(errorBody, ApiResponse::class.java)
                apiResponse?.error?.message ?: "Unknown error occurred"
            } else {
                "No error body"
            }
        } catch (e: IOException) {
            "IO error: ${e.localizedMessage}"
        } catch (e: Exception) {
            "Error parsing response: ${e.localizedMessage}"
        }
    }
}