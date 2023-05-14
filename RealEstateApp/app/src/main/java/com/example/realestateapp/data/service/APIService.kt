package com.example.realestateapp.data.service

import com.example.realestateapp.data.apiresult.ResponseAPI
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.PagingItem
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.data.models.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * Created by tuyen.dang on 4/30/2023.
 */

interface APIService {
    //launcher follow
    @POST("/api/Accounts/login")
    @JvmSuppressWildcards
    suspend fun signIn(@Body options: Map<String, Any>): Response<ResponseAPI<User?>>

    @POST("/api/Accounts/register")
    @JvmSuppressWildcards
    suspend fun signUp(@Body options: Map<String, Any>): Response<ResponseAPI<Boolean>>

    //home follow
    @GET("/api/PropertyTypes")
    @JvmSuppressWildcards
    suspend fun getTypes(): Response<ResponseAPI<MutableList<ItemChoose>>>

    @POST("/api/Posts/options")
    @JvmSuppressWildcards
    suspend fun getPostsWOptions(@Body options: Map<String, Any>): Response<ResponseAPI<PagingItem<RealEstateList>>>
}
