package com.example.realestateapp.data.service

import com.example.realestateapp.data.apiresult.ResponseAPI
import com.example.realestateapp.data.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("/api/Posts/{postId}/detail-info/{userId}")
    @JvmSuppressWildcards
    suspend fun getPostDetailById(
        @Path("postId") idPost: String,
        @Path("userId") idUser: String
    ): Response<ResponseAPI<RealEstateDetail>>

    @GET("/api/Posts/{postId}/same-price/{userId}")
    @JvmSuppressWildcards
    suspend fun getPostSamePrice(
        @Path("postId") idPost: String,
        @Path("userId") idUser: String
    ): Response<ResponseAPI<MutableList<RealEstateList>>>

    @GET("/api/Posts/{postId}/same-cluster/{userId}")
    @JvmSuppressWildcards
    suspend fun getPostSameCluster(
        @Path("postId") idPost: String,
        @Path("userId") idUser: String
    ): Response<ResponseAPI<MutableList<RealEstateList>>>

    @GET("/api/Addresses/{provinceId}/districts")
    @JvmSuppressWildcards
    suspend fun getDistricts(
        @Path("provinceId") provinceId: String
    ): Response<ResponseAPI<MutableList<ItemChoose>>>

    @GET("/api/Addresses/{districtId}/wards")
    @JvmSuppressWildcards
    suspend fun getWards(
        @Path("districtId") districtId: String
    ): Response<ResponseAPI<MutableList<ItemChoose>>>

    @GET("/api/Streets/district/{districtId}")
    @JvmSuppressWildcards
    suspend fun getStreets(
        @Path("districtId") districtId: String,
        @Query("search") filter: String
    ): Response<ResponseAPI<MutableList<ItemChoose>>>

    @PUT("/api/Posts/search/{userId}")
    @JvmSuppressWildcards
    suspend fun searchPostWithOptions(
        @Path("userId") idUser: String,
        @Body options: Map<String, Any?>
    ): Response<ResponseAPI<PagingItem<RealEstateList>>>

    @PUT("/api/Posts/save-post")
    @JvmSuppressWildcards
    suspend fun updateSavePost(
        @Body options: Map<String, Any>
    ): Response<ResponseAPI<Any?>>

    @GET("api/Posts/get-post-saved")
    @JvmSuppressWildcards
    suspend fun getPostSaved(
        @Query("userId") idUser: Int,
        @Query("pageIndex") pageIndex: Int,
        @Query("pageSize") pageSize: Int
    ): Response<ResponseAPI<PagingItem<RealEstateList>>>

    @GET("/api/Posts/created-by-user")
    @JvmSuppressWildcards
    suspend fun getPostCreatedByUser(
        @Query("userId") idUser: Int,
        @Query("pageIndex") pageIndex: Int,
        @Query("pageSize") pageSize: Int
    ): Response<ResponseAPI<PagingItem<RealEstateList>>>
}
