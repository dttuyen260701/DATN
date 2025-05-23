package com.example.realestateapp.data.fake

import com.example.realestateapp.data.apiresult.ApiResultWrapper
import com.example.realestateapp.data.apiresult.ResponseAPI
import com.example.realestateapp.data.models.ComboOption
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.PagingItem
import com.example.realestateapp.data.models.PredictResult
import com.example.realestateapp.data.models.RealEstateDetail
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.data.models.User
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.util.ConstantTest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class FakeAppRepository @Inject constructor() : AppRepository {
    override suspend fun signIn(
        email: String,
        password: String
    ): Flow<ApiResultWrapper<User?>> = flow {
        emit(
            if (email == ConstantTest.DefaultTestLaunchValue.email
                && password == ConstantTest.DefaultTestLaunchValue.password
            ) {
                ApiResultWrapper.Success(
                    value = ResponseAPI(
                        isSuccess = true,
                        errorMessage = null,
                        body = User(
                            email = email
                        )
                    )
                )
            } else {
                ApiResultWrapper.ResponseCodeError(
                    ConstantTest.DefaultTestLaunchValue.messageErrorLogin
                )
            }
        )
    }


    override suspend fun signUp(
        name: String,
        phone: String,
        email: String,
        password: String
    ): Flow<ApiResultWrapper<Boolean>> = flow {
        val result = name.isNotBlank() && phone.isNotBlank()
                && email == ConstantTest.DefaultTestLaunchValue.email
                && password == ConstantTest.DefaultTestLaunchValue.password
        emit(
            if (result) {
                ApiResultWrapper.Success(
                    value = ResponseAPI(
                        isSuccess = true,
                        errorMessage = "",
                        body = true
                    )
                )
            } else {
                ApiResultWrapper.ResponseCodeError(
                    ConstantTest.DefaultTestLaunchValue.messageErrorLogin
                )
            }
        )
    }

    override suspend fun getTypes(): Flow<ApiResultWrapper<MutableList<ItemChoose>>> {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override suspend fun getPostDetailById(
        idPost: String,
        idUser: String
    ): Flow<ApiResultWrapper<RealEstateDetail>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostSamePrice(
        idPost: String,
        idUser: String
    ): Flow<ApiResultWrapper<MutableList<RealEstateList>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostSameCluster(
        idPost: String,
        idUser: String
    ): Flow<ApiResultWrapper<MutableList<RealEstateList>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDistricts(
        provinceId: String
    ): Flow<ApiResultWrapper<MutableList<ItemChoose>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getWards(
        districtId: String
    ): Flow<ApiResultWrapper<MutableList<ItemChoose>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getStreets(
        districtId: String,
        filter: String
    ): Flow<ApiResultWrapper<MutableList<ItemChoose>>> {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override suspend fun updateSavePost(idPost: Int, idUser: Int): Flow<ApiResultWrapper<Any?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostSaved(
        idUser: Int,
        pageIndex: Int,
        pageSize: Int,
        filter: String
    ): Flow<ApiResultWrapper<PagingItem<RealEstateList>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostCreatedByUser(
        idUser: Int,
        pageIndex: Int,
        pageSize: Int,
        filter: String
    ): Flow<ApiResultWrapper<PagingItem<RealEstateList>>> {
        TODO("Not yet implemented")
    }

    override suspend fun uploadImage(
        image: File
    ): Flow<ApiResultWrapper<String>> {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override suspend fun getComboOptions(): Flow<ApiResultWrapper<MutableList<ComboOption>>> {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override suspend fun changePassWord(
        idUser: Int,
        oldPassword: String,
        newPassword: String
    ): Flow<ApiResultWrapper<Any?>> = flow {
        emit(
            ApiResultWrapper.Success(
                value = ResponseAPI(
                    isSuccess = (idUser != -1 && oldPassword != newPassword),
                    errorMessage = "",
                    body = null
                )
            )
        )
    }

    override suspend fun getInformationUser(
        idUser: Int
    ): Flow<ApiResultWrapper<User>> = flow {
        emit(
            ApiResultWrapper.Success(
                value = ResponseAPI(
                    isSuccess = true,
                    errorMessage = null,
                    body = User()
                )
            )
        )
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
    ): Flow<ApiResultWrapper<User?>> = flow {
        emit(
            ApiResultWrapper.Success(
                value = ResponseAPI(
                    isSuccess = true,
                    errorMessage = null,
                    body = User(
                        id = 1,
                        fullName = "Testing Edit",
                        dateOfBirth = "07/07/07"
                    )
                )
            )
        )
    }

    override suspend fun createReport(
        postId: Int,
        reporterId: Int,
        description: String
    ): Flow<ApiResultWrapper<Any?>> {
        TODO("Not yet implemented")
    }
}