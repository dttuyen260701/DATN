package com.example.realestateapp.data.repository

import com.example.realestateapp.data.apiresult.ApiResultWrapper
import com.example.realestateapp.data.models.*
import kotlinx.coroutines.flow.Flow

/**
 * Created by tuyen.dang on 4/30/2023.
 */

interface AppRepository {
    //launcher follow
    suspend fun signIn(
        email: String,
        password: String,
        showLoading: Boolean = true
    ): Flow<ApiResultWrapper<User?>>

    suspend fun signUp(
        name: String,
        phone: String,
        email: String,
        password: String
    ): Flow<ApiResultWrapper<Boolean>>

    //home follow
    suspend fun getTypes(showLoading: Boolean = true): Flow<ApiResultWrapper<MutableList<ItemChoose>>>

    suspend fun getPostsWOptions(
        pageIndex: Int,
        pageSize: Int,
        isMostView: Boolean,
        typePropertyIds: MutableList<Int>,
        isLatest: Boolean,
        isHighestPrice: Boolean,
        isLowestPrice: Boolean,
        userId: Int = 0,
        showLoading: Boolean = true
    ): Flow<ApiResultWrapper<PagingItem<RealEstateList>>>

    suspend fun getPostDetailById(
        idPost: String,
        idUser: String,
        showLoading: Boolean = true
    ): Flow<ApiResultWrapper<RealEstateDetail>>

    suspend fun getPostSamePrice(
        idPost: String,
        idUser: String,
        showLoading: Boolean = true
    ): Flow<ApiResultWrapper<MutableList<RealEstateList>>>

    suspend fun getPostSameCluster(
        idPost: String,
        idUser: String,
        showLoading: Boolean = true
    ): Flow<ApiResultWrapper<MutableList<RealEstateList>>>

    //pick Address
    suspend fun getDistricts(
        provinceId: String = "1",
        showLoading: Boolean = false
    ): Flow<ApiResultWrapper<MutableList<ItemChoose>>>

    suspend fun getWards(
        districtId: String,
        showLoading: Boolean = false
    ): Flow<ApiResultWrapper<MutableList<ItemChoose>>>

    suspend fun getStreets(
        districtId: String,
        filter: String,
        showLoading: Boolean = false
    ): Flow<ApiResultWrapper<MutableList<ItemChoose>>>

    suspend fun searchPostWithOptions(
        idUser: String = "",
        minPrice: Int,
        maxPrice: Int,
        minBedRoom: Int,
        maxBedRoom: Int,
        minWidth: Int,
        maxWidth: Int,
        minSquare: Int,
        maxSquare: Int,
        minLength: Int,
        maxLength: Int,
        minFloor: Int,
        maxFloor: Int,
        minKitchen: Int,
        maxKitchen: Int,
        propertyTypeId: MutableList<Int>,
        legalId: Int,
        carParking: Boolean? = null,
        directionId: Int,
        rooftop: Boolean? = null,
        districtId: Int? = null,
        wardId: Int? = null,
        streetId: Int? = null,
        minWidthRoad: Int,
        maxWidthRoad: Int,
        pageIndex: Int,
        pageSize: Int,
        search: String,
        optionSort: Int,
        showLoading: Boolean = true
    ): Flow<ApiResultWrapper<PagingItem<RealEstateList>>>

    suspend fun updateSavePost(
        idPost: Int,
        idUser: Int
    ): Flow<ApiResultWrapper<Any?>>

    suspend fun getPostSaved(
        idUser: Int,
        pageIndex: Int,
        pageSize: Int,
        showLoading: Boolean = true
    ): Flow<ApiResultWrapper<PagingItem<RealEstateList>>>

    suspend fun getPostCreatedByUser(
        idUser: Int,
        pageIndex: Int,
        pageSize: Int,
        showLoading: Boolean = true
    ): Flow<ApiResultWrapper<PagingItem<RealEstateList>>>
}
