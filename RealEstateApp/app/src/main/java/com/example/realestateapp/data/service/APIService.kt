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
    @JvmSuppressWildcards
    suspend fun signIn(@Body options: Map<String, Any>): Response<ResponseAPI<User?>>

    @POST("/api/Accounts/register")
    @JvmSuppressWildcards
    suspend fun signUp(@Body options: Map<String, Any>): Response<ResponseAPI<Boolean>>
}
