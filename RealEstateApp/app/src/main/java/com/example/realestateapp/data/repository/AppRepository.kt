package com.example.realestateapp.data.repository

import com.example.realestateapp.data.apiresult.ApiResultWrapper
import com.example.realestateapp.data.models.*
import kotlinx.coroutines.flow.Flow
import java.io.File

/**
 * Created by tuyen.dang on 4/30/2023.
 */

interface AppRepository {
    //launcher follow
    suspend fun signIn(
        email: String,
        password: String
    ): Flow<ApiResultWrapper<User?>>

    suspend fun signUp(
        name: String,
        phone: String,
        email: String,
        password: String
    ): Flow<ApiResultWrapper<Boolean>>

    //home follow
    suspend fun getTypes(): Flow<ApiResultWrapper<MutableList<ItemChoose>>>

    suspend fun getPostsWOptions(
        pageIndex: Int,
        pageSize: Int,
        isMostView: Boolean,
        typePropertyIds: MutableList<Int>,
        isLatest: Boolean,
        isHighestPrice: Boolean,
        isLowestPrice: Boolean,
        userId: Int = 0
    ): Flow<ApiResultWrapper<PagingItem<RealEstateList>>>

    suspend fun getPostDetailById(
        idPost: String,
        idUser: String
    ): Flow<ApiResultWrapper<RealEstateDetail>>

    suspend fun getPostSamePrice(
        idPost: String,
        idUser: String
    ): Flow<ApiResultWrapper<MutableList<RealEstateList>>>

    suspend fun getPostSameCluster(
        idPost: String,
        idUser: String
    ): Flow<ApiResultWrapper<MutableList<RealEstateList>>>

    //pick Address
    suspend fun getDistricts(
        provinceId: String = "1"
    ): Flow<ApiResultWrapper<MutableList<ItemChoose>>>

    suspend fun getWards(
        districtId: String
    ): Flow<ApiResultWrapper<MutableList<ItemChoose>>>

    suspend fun getStreets(
        districtId: String,
        filter: String
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
        optionSort: Int
    ): Flow<ApiResultWrapper<PagingItem<RealEstateList>>>

    suspend fun updateSavePost(
        idPost: Int,
        idUser: Int
    ): Flow<ApiResultWrapper<Any?>>

    suspend fun getPostSaved(
        idUser: Int,
        pageIndex: Int,
        pageSize: Int,
        filter: String
    ): Flow<ApiResultWrapper<PagingItem<RealEstateList>>>

    suspend fun getPostCreatedByUser(
        idUser: Int,
        pageIndex: Int,
        pageSize: Int,
        filter: String
    ): Flow<ApiResultWrapper<PagingItem<RealEstateList>>>

    suspend fun uploadImage(
        image: File
    ): Flow<ApiResultWrapper<String>>

    suspend fun getPredictPrice(
        bedRoom: Int,
        width: Float,
        acreage: Float,
        length: Float,
        floorNumber: Int,
        kitchen: Int,
        diningRoom: Int,
        propertyTypeId: Int,
        legalTypeId: Int,
        carParking: Boolean,
        directionId: Int,
        rooftop: Boolean,
        districtId: Int,
        wardId: Int,
        streetId: Int,
        widthRoad: Float
    ): Flow<ApiResultWrapper<PredictResult>>

    suspend fun getComboOptions(): Flow<ApiResultWrapper<MutableList<ComboOption>>>

    suspend fun createPost(
        title: String,
        description: String,
        ownerId: Int,
        price: Double,
        suggestedPrice: Double,
        directionId: Int,
        width: Double,
        acreage: Double,
        parkingSpace: Boolean,
        streetInFront: Double,
        length: Double,
        bedroomNumber: Int,
        kitchen: Int,
        rooftop: Boolean,
        floorNumber: Int,
        diningRoom: Int,
        legalTypeId: Int,
        isOwner: Boolean = true,
        detail: String = "",
        provinceId: Int = 1,
        districtId: Int,
        wardId: Int,
        streetId: Int,
        longitude: Double,
        latitude: Double,
        images: MutableList<String>,
        propertyTypeId: Int,
        cluster: Int,
        comboOptionId: Int
    ): Flow<ApiResultWrapper<Any?>>

    suspend fun updatePost(
        idPost: Int,
        title: String,
        description: String,
        price: Double,
        width: Double,
        acreage: Double,
        parkingSpace: Boolean,
        streetInFront: Double,
        length: Double,
        bedroomNumber: Int,
        diningRoom: Int,
        kitchen: Int,
        rooftop: Boolean,
        floorNumber: Int,
        legalTypeId: Int,
        detailAddress: String,
        districtId: Int,
        wardId: Int,
        streetId: Int,
        longitude: Double,
        latitude: Double,
        listNewImages: MutableList<String>,
        propertyTypeId: Int,
        comboOptionId: Int
    ): Flow<ApiResultWrapper<Any?>>

    suspend fun changePassWord(
        idUser: Int,
        oldPassword: String,
        newPassword: String
    ): Flow<ApiResultWrapper<Any?>>

    suspend fun getInformationUser(
        idUser: Int
    ): Flow<ApiResultWrapper<User>>

    suspend fun updateUser(
        userId: Int,
        fullName: String,
        dateOfBirth: String,
        gender: Int,
        addressDetail: String,
        wardId: Int,
        districtId: Int,
        newImage: String
    ): Flow<ApiResultWrapper<User?>>

    suspend fun createReport(
        postId: Int,
        reporterId: Int,
        description: String
    ): Flow<ApiResultWrapper<Any?>>
}
