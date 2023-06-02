package com.example.realestateapp.data.repository

import com.example.realestateapp.data.apiresult.ApiResultWrapper
import com.example.realestateapp.data.datasource.RetrofitDataSource
import com.example.realestateapp.data.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import java.io.File
import javax.inject.Inject

/**
 * Created by tuyen.dang on 4/30/2023.
 */

class AppRepositoryImpl @Inject constructor(
    private val dataSource: RetrofitDataSource
) : AppRepository {
    override suspend fun signIn(
        email: String,
        password: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<User?>> {
        return flow {
            emit(
                dataSource.signIn(
                    email = email,
                    password = password
                )
            )
        }.onStart { if (showLoading) emit(ApiResultWrapper.Loading) }
    }

    override suspend fun signUp(
        name: String,
        phone: String,
        email: String,
        password: String
    ): Flow<ApiResultWrapper<Boolean>> {
        return flow {
            emit(
                dataSource.signUp(
                    name = name,
                    phone = phone,
                    email = email,
                    password = password
                )
            )
        }.onStart { emit(ApiResultWrapper.Loading) }
    }

    //home follow
    override suspend fun getTypes(showLoading: Boolean): Flow<ApiResultWrapper<MutableList<ItemChoose>>> {
        return flow {
            emit(dataSource.getTypes())
        }.onStart { if (showLoading) emit(ApiResultWrapper.Loading) }
    }

    override suspend fun getPostsWOptions(
        pageIndex: Int,
        pageSize: Int,
        isMostView: Boolean,
        typePropertyIds: MutableList<Int>,
        isLatest: Boolean,
        isHighestPrice: Boolean,
        isLowestPrice: Boolean,
        userId: Int,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<PagingItem<RealEstateList>>> {
        return flow {
            emit(
                dataSource.getPostsWOptions(
                    pageIndex = pageIndex,
                    pageSize = pageSize,
                    isMostView = isMostView,
                    typePropertyIds = typePropertyIds,
                    isLatest = isLatest,
                    isHighestPrice = isHighestPrice,
                    isLowestPrice = isLowestPrice,
                    userId = userId
                )
            )
        }.onStart { if (showLoading) emit(ApiResultWrapper.Loading) }
    }

    override suspend fun getPostDetailById(
        idPost: String,
        idUser: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<RealEstateDetail>> {
        return flow {
            emit(
                dataSource.getPostDetailById(
                    idPost = idPost,
                    idUser = idUser
                )
            )
        }.onStart { if (showLoading) emit(ApiResultWrapper.Loading) }
    }

    override suspend fun getPostSamePrice(
        idPost: String,
        idUser: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<MutableList<RealEstateList>>> {
        return flow {
            emit(
                dataSource.getPostSamePrice(
                    idPost = idPost,
                    idUser = idUser
                )
            )
        }.onStart { if (showLoading) emit(ApiResultWrapper.Loading) }
    }

    override suspend fun getPostSameCluster(
        idPost: String,
        idUser: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<MutableList<RealEstateList>>> {
        return flow {
            emit(
                dataSource.getPostSameCluster(
                    idPost = idPost,
                    idUser = idUser
                )
            )
        }.onStart { if (showLoading) emit(ApiResultWrapper.Loading) }
    }

    override suspend fun getDistricts(
        provinceId: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<MutableList<ItemChoose>>> {
        return flow {
            emit(
                dataSource.getDistricts(provinceId)
            )
        }.onStart { if (showLoading) emit(ApiResultWrapper.Loading) }
    }

    override suspend fun getWards(
        districtId: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<MutableList<ItemChoose>>> {
        return flow {
            emit(
                dataSource.getWards(districtId)
            )
        }.onStart { if (showLoading) emit(ApiResultWrapper.Loading) }
    }

    override suspend fun getStreets(
        districtId: String,
        filter: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<MutableList<ItemChoose>>> {
        return flow {
            emit(
                dataSource.getStreets(districtId, filter)
            )
        }.onStart { if (showLoading) emit(ApiResultWrapper.Loading) }
    }

    override suspend fun searchPostWithOptions(
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
        optionSort: Int,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<PagingItem<RealEstateList>>> {
        return flow {
            emit(
                dataSource.searchPostWithOptions(
                    idUser = idUser,
                    minPrice = minPrice,
                    maxPrice = maxPrice,
                    minBedRoom = minBedRoom,
                    maxBedRoom = maxBedRoom,
                    minWidth = minWidth,
                    maxWidth = maxWidth,
                    minSquare = minSquare,
                    maxSquare = maxSquare,
                    minLength = minLength,
                    maxLength = maxLength,
                    minFloor = minFloor,
                    maxFloor = maxFloor,
                    minKitchen = minKitchen,
                    maxKitchen = maxKitchen,
                    propertyTypeId = propertyTypeId,
                    legalId = legalId,
                    carParking = carParking,
                    directionId = directionId,
                    rooftop = rooftop,
                    districtId = districtId,
                    wardId = wardId,
                    streetId = streetId,
                    minWidthRoad = minWidthRoad,
                    maxWidthRoad = maxWidthRoad,
                    pageIndex = pageIndex,
                    pageSize = pageSize,
                    search = search,
                    optionSort = optionSort
                )
            )
        }.onStart { if (showLoading) emit(ApiResultWrapper.Loading) }
    }

    override suspend fun updateSavePost(
        idPost: Int,
        idUser: Int
    ): Flow<ApiResultWrapper<Any?>> {
        return flow {
            emit(
                dataSource.updateSavePost(
                    idPost = idPost,
                    idUser = idUser
                )
            )
        }
    }

    override suspend fun getPostSaved(
        idUser: Int,
        pageIndex: Int,
        pageSize: Int,
        filter: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<PagingItem<RealEstateList>>> {
        return flow {
            emit(
                dataSource.getPostSaved(
                    idUser = idUser,
                    pageIndex = pageIndex,
                    pageSize = pageSize,
                    filter = filter
                )
            )
        }.onStart { if (showLoading) emit(ApiResultWrapper.Loading) }
    }

    override suspend fun getPostCreatedByUser(
        idUser: Int,
        pageIndex: Int,
        pageSize: Int,
        filter: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<PagingItem<RealEstateList>>> {
        return flow {
            emit(
                dataSource.getPostCreatedByUser(
                    idUser = idUser,
                    pageIndex = pageIndex,
                    pageSize = pageSize,
                    filter = filter
                )
            )
        }.onStart { if (showLoading) emit(ApiResultWrapper.Loading) }
    }

    override suspend fun uploadImage(
        image: File,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<String>> {
        return flow {
            emit(
                dataSource.uploadImage(image)
            )
        }.onStart { if (showLoading) emit(ApiResultWrapper.Loading) }
    }

    override suspend fun getPredictPrice(
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
        widthRoad: Float,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<PredictResult>> {
        return flow {
            emit(
                dataSource.getPredictPrice(
                    bedRoom = bedRoom,
                    width = width,
                    acreage = acreage,
                    length = length,
                    floorNumber = floorNumber,
                    kitchen = kitchen,
                    diningRoom = diningRoom,
                    propertyTypeId = propertyTypeId,
                    legalTypeId = legalTypeId,
                    carParking = carParking,
                    directionId = directionId,
                    rooftop = rooftop,
                    districtId = districtId,
                    wardId = wardId,
                    streetId = streetId,
                    widthRoad = widthRoad
                )
            )
        }.onStart { if (showLoading) emit(ApiResultWrapper.Loading) }
    }
}
