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
        password: String
    ): Flow<ApiResultWrapper<User?>> {
        return flow {
            emit(
                dataSource.signIn(
                    email = email,
                    password = password
                )
            )
        }
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
    override suspend fun getTypes(): Flow<ApiResultWrapper<MutableList<ItemChoose>>> {
        return flow {
            emit(dataSource.getTypes())
        }
    }

    override suspend fun getPostsWOptions(
        pageIndex: Int,
        pageSize: Int,
        isMostView: Boolean,
        typePropertyIds: MutableList<Int>,
        isLatest: Boolean,
        isHighestPrice: Boolean,
        isLowestPrice: Boolean,
        userId: Int
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
        }
    }

    override suspend fun getPostDetailById(
        idPost: String,
        idUser: String
    ): Flow<ApiResultWrapper<RealEstateDetail>> {
        return flow {
            emit(
                dataSource.getPostDetailById(
                    idPost = idPost,
                    idUser = idUser
                )
            )
        }
    }

    override suspend fun getPostSamePrice(
        idPost: String,
        idUser: String
    ): Flow<ApiResultWrapper<MutableList<RealEstateList>>> {
        return flow {
            emit(
                dataSource.getPostSamePrice(
                    idPost = idPost,
                    idUser = idUser
                )
            )
        }
    }

    override suspend fun getPostSameCluster(
        idPost: String,
        idUser: String
    ): Flow<ApiResultWrapper<MutableList<RealEstateList>>> {
        return flow {
            emit(
                dataSource.getPostSameCluster(
                    idPost = idPost,
                    idUser = idUser
                )
            )
        }
    }

    override suspend fun getDistricts(
        provinceId: String
    ): Flow<ApiResultWrapper<MutableList<ItemChoose>>> {
        return flow {
            emit(
                dataSource.getDistricts(provinceId)
            )
        }
    }

    override suspend fun getWards(
        districtId: String
    ): Flow<ApiResultWrapper<MutableList<ItemChoose>>> {
        return flow {
            emit(
                dataSource.getWards(districtId)
            )
        }
    }

    override suspend fun getStreets(
        districtId: String,
        filter: String
    ): Flow<ApiResultWrapper<MutableList<ItemChoose>>> {
        return flow {
            emit(
                dataSource.getStreets(districtId, filter)
            )
        }
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
        optionSort: Int
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
        }
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
        filter: String
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
        }
    }

    override suspend fun getPostCreatedByUser(
        idUser: Int,
        pageIndex: Int,
        pageSize: Int,
        filter: String
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
        }
    }

    override suspend fun uploadImage(
        image: File
    ): Flow<ApiResultWrapper<String>> {
        return flow {
            emit(
                dataSource.uploadImage(image)
            )
        }
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
        widthRoad: Float
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
        }
    }

    override suspend fun getComboOptions(): Flow<ApiResultWrapper<MutableList<ComboOption>>> {
        return flow {
            emit(
                dataSource.getComboOptions()
            )
        }
    }

    override suspend fun createPost(
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
    ): Flow<ApiResultWrapper<Any?>> {
        return flow {
            emit(
                dataSource.createPost(
                    title = title,
                    description = description,
                    ownerId = ownerId,
                    price = price,
                    suggestedPrice = suggestedPrice,
                    directionId = directionId,
                    width = width,
                    acreage = acreage,
                    parkingSpace = parkingSpace,
                    streetInFront = streetInFront,
                    length = length,
                    bedroomNumber = bedroomNumber,
                    kitchen = kitchen,
                    rooftop = rooftop,
                    floorNumber = floorNumber,
                    diningRoom = diningRoom,
                    legalTypeId = legalTypeId,
                    isOwner = isOwner,
                    detail = detail,
                    provinceId = provinceId,
                    districtId = districtId,
                    wardId = wardId,
                    streetId = streetId,
                    longitude = longitude,
                    latitude = latitude,
                    images = images,
                    propertyTypeId = propertyTypeId,
                    cluster = cluster,
                    comboOptionId = comboOptionId
                )
            )
        }
    }

    override suspend fun updatePost(
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
    ): Flow<ApiResultWrapper<Any?>> {
        return flow {
            emit(
                dataSource.updatePost(
                    idPost = idPost,
                    title = title,
                    description = description,
                    price = price,
                    width = width,
                    acreage = acreage,
                    parkingSpace = parkingSpace,
                    streetInFront = streetInFront,
                    length = length,
                    bedroomNumber = bedroomNumber,
                    kitchen = kitchen,
                    rooftop = rooftop,
                    floorNumber = floorNumber,
                    diningRoom = diningRoom,
                    legalTypeId = legalTypeId,
                    detailAddress = detailAddress,
                    districtId = districtId,
                    wardId = wardId,
                    streetId = streetId,
                    longitude = longitude,
                    latitude = latitude,
                    listNewImages = listNewImages,
                    propertyTypeId = propertyTypeId,
                    comboOptionId = comboOptionId
                )
            )
        }
    }

    override suspend fun changePassWord(
        idUser: Int,
        oldPassword: String,
        newPassword: String
    ): Flow<ApiResultWrapper<Any?>> {
        return flow {
            emit(
                dataSource.changePassWord(
                    idUser = idUser,
                    oldPassword = oldPassword,
                    newPassword = newPassword
                )
            )
        }
    }

    override suspend fun getInformationUser(
        idUser: Int
    ): Flow<ApiResultWrapper<User>> {
        return flow {
            emit(
                dataSource.getInformationUser(idUser)
            )
        }
    }

    override suspend fun updateUser(
        userId: Int,
        fullName: String,
        dateOfBirth: String,
        gender: Int,
        addressDetail: String,
        wardId: Int,
        districtId: Int,
        newImage: String
    ): Flow<ApiResultWrapper<User?>> {
        return flow {
            emit(
                dataSource.updateUser(
                    userId = userId,
                    fullName = fullName,
                    dateOfBirth = dateOfBirth,
                    gender = gender,
                    addressDetail = addressDetail,
                    wardId = wardId,
                    districtId = districtId,
                    newImage = newImage
                )
            )
        }
    }

    override suspend fun createReport(
        postId: Int,
        reporterId: Int,
        description: String
    ): Flow<ApiResultWrapper<Any?>> {
        return flow {
            emit(
                dataSource.createReport(
                    postId = postId,
                    reporterId = reporterId,
                    description = description
                )
            )
        }
    }
}
