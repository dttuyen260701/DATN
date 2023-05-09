package com.example.realestateapp.data.service

import com.example.realestateapp.data.apiresult.ResponseAPI
import com.example.realestateapp.data.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by tuyen.dang on 4/30/2023.
 */

interface APIService {
    @POST("/api/Accounts/login")
    suspend fun login(@Body options: Map<String, String>, @Body isAdmin: Boolean = false): Response<ResponseAPI<User>>
}
