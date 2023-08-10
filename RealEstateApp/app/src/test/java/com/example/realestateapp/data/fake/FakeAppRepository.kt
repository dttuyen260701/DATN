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
        password: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<User?>> = flow {
        emit(
            if (email == ConstantTest.DefaultTestLaunchValue.email && password == ConstantTest.DefaultTestLaunchValue.password) {
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
                ApiResultWrapper.Success(
                    value = ResponseAPI(
                        isSuccess = false,
                        errorMessage = ConstantTest.DefaultTestLaunchValue.messageErrorLogin,
                        body = null
                    )
                )
            }
        )
    }


    override suspend fun signUp(
        name: String,
        phone: String,
        email: String,
        password: String
    ): Flow<ApiResultWrapper<Boolean>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTypes(showLoading: Boolean): Flow<ApiResultWrapper<MutableList<ItemChoose>>> {
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
        userId: Int,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<PagingItem<RealEstateList>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostDetailById(
        idPost: String,
        idUser: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<RealEstateDetail>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostSamePrice(
        idPost: String,
        idUser: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<MutableList<RealEstateList>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostSameCluster(
        idPost: String,
        idUser: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<MutableList<RealEstateList>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getDistricts(
        provinceId: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<MutableList<ItemChoose>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getWards(
        districtId: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<MutableList<ItemChoose>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getStreets(
        districtId: String,
        filter: String,
        showLoading: Boolean
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
        optionSort: Int,
        showLoading: Boolean
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
        filter: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<PagingItem<RealEstateList>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getPostCreatedByUser(
        idUser: Int,
        pageIndex: Int,
        pageSize: Int,
        filter: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<PagingItem<RealEstateList>>> {
        TODO("Not yet implemented")
    }

    override suspend fun uploadImage(
        image: File,
        showLoading: Boolean
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
        widthRoad: Float,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<PredictResult>> {
        TODO("Not yet implemented")
    }

    override suspend fun getComboOptions(showLoading: Boolean): Flow<ApiResultWrapper<MutableList<ComboOption>>> {
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
        comboOptionId: Int,
        showLoading: Boolean
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
        comboOptionId: Int,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<Any?>> {
        TODO("Not yet implemented")
    }

    override suspend fun changePassWord(
        idUser: Int,
        oldPassword: String,
        newPassword: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<Any?>> {
        TODO("Not yet implemented")
    }

    override suspend fun getInformationUser(
        idUser: Int,
        showLoading: Boolean
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
        newImage: String,
        showLoading: Boolean
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
        description: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<Any?>> {
        TODO("Not yet implemented")
    }
}