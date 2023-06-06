package com.example.realestateapp.data.datasource

import com.example.realestateapp.data.apiresult.ApiResultWrapper
import com.example.realestateapp.data.models.*
import java.io.File

/**
 * Created by tuyen.dang on 4/30/2023.
 */

interface RetrofitDataSource {
    //launcher follow
    suspend fun signIn(email: String, password: String): ApiResultWrapper<User?>

    suspend fun signUp(
        name: String,
        phone: String,
        email: String,
        password: String
    ): ApiResultWrapper<Boolean>

    //home follow
    suspend fun getTypes(): ApiResultWrapper<MutableList<ItemChoose>>

    suspend fun getPostsWOptions(
        pageIndex: Int,
        pageSize: Int,
        isMostView: Boolean,
        typePropertyIds: MutableList<Int>,
        isLatest: Boolean,
        isHighestPrice: Boolean,
        isLowestPrice: Boolean,
        userId: Int
    ): ApiResultWrapper<PagingItem<RealEstateList>>

    suspend fun getPostDetailById(
        idPost: String,
        idUser: String
    ): ApiResultWrapper<RealEstateDetail>

    suspend fun getPostSamePrice(
        idPost: String,
        idUser: String
    ): ApiResultWrapper<MutableList<RealEstateList>>

    suspend fun getPostSameCluster(
        idPost: String,
        idUser: String
    ): ApiResultWrapper<MutableList<RealEstateList>>

    suspend fun getDistricts(
        provinceId: String
    ): ApiResultWrapper<MutableList<ItemChoose>>

    suspend fun getWards(
        districtId: String
    ): ApiResultWrapper<MutableList<ItemChoose>>

    suspend fun getStreets(
        districtId: String,
        filter: String
    ): ApiResultWrapper<MutableList<ItemChoose>>

    suspend fun searchPostWithOptions(
        idUser: String,
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
        carParking: Boolean?,
        directionId: Int,
        rooftop: Boolean?,
        districtId: Int?,
        wardId: Int?,
        streetId: Int?,
        minWidthRoad: Int,
        maxWidthRoad: Int,
        pageIndex: Int,
        pageSize: Int,
        search: String,
        optionSort: Int
    ): ApiResultWrapper<PagingItem<RealEstateList>>

    suspend fun updateSavePost(
        idPost: Int,
        idUser: Int
    ): ApiResultWrapper<Any?>

    suspend fun getPostSaved(
        idUser: Int,
        pageIndex: Int,
        pageSize: Int,
        filter: String
    ): ApiResultWrapper<PagingItem<RealEstateList>>

    suspend fun getPostCreatedByUser(
        idUser: Int,
        pageIndex: Int,
        pageSize: Int,
        filter: String
    ): ApiResultWrapper<PagingItem<RealEstateList>>

    suspend fun uploadImage(
        image: File
    ): ApiResultWrapper<String>

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
    ): ApiResultWrapper<PredictResult>

    suspend fun getComboOptions()
            : ApiResultWrapper<MutableList<ComboOption>>

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
        isOwner: Boolean,
        detail: String,
        provinceId: Int,
        districtId: Int,
        wardId: Int,
        streetId: Int,
        longitude: Double,
        latitude: Double,
        images: MutableList<String>,
        propertyTypeId: Int,
        cluster: Int,
        comboOptionId: Int
    ): ApiResultWrapper<Any?>

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
    ): ApiResultWrapper<Any?>

    suspend fun changePassWord(
        idUser: Int,
        oldPassword: String,
        newPassword: String
    ): ApiResultWrapper<Any?>
}
