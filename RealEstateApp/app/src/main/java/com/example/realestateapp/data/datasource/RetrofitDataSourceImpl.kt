package com.example.realestateapp.data.datasource

import com.example.realestateapp.data.apiresult.ApiResultWrapper
import com.example.realestateapp.data.models.*
import com.example.realestateapp.data.network.SafeAPI
import com.example.realestateapp.data.service.APIService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

/**
 * Created by tuyen.dang on 4/30/2023.
 */

class RetrofitDataSourceImpl @Inject constructor(
    private val apiService: APIService
) : RetrofitDataSource, SafeAPI() {

    override suspend fun signIn(email: String, password: String): ApiResultWrapper<User?> {
        val options: MutableMap<String, Any> = HashMap()
        options["userName"] = email
        options["password"] = password
        options["isAdmin"] = false
        return callApi {
            apiService.signIn(options)
        }
    }

    override suspend fun signUp(
        name: String,
        phone: String,
        email: String,
        password: String
    ): ApiResultWrapper<Boolean> {
        val options: MutableMap<String, Any> = HashMap()
        options["name"] = name
        options["email"] = email
        options["phone"] = phone
        options["password"] = password
        options["dateOfBirth"] = "01/01/2023"
        return callApi {
            apiService.signUp(options)
        }
    }

    override suspend fun getTypes(): ApiResultWrapper<MutableList<ItemChoose>> {
        return callApi {
            apiService.getTypes()
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
    ): ApiResultWrapper<PagingItem<RealEstateList>> {
        val options: MutableMap<String, Any> = HashMap()
        options["pageIndex"] = pageIndex
        options["pageSize"] = pageSize
        options["isMostView"] = isMostView
        options["typePropertyIds"] = typePropertyIds
        options["isLatest"] = isLatest
        options["isHighestPrice"] = isHighestPrice
        options["isLowestPrice"] = isLowestPrice
        options["userId"] = userId
        return callApi {
            apiService.getPostsWOptions(options)
        }
    }

    override suspend fun getPostDetailById(
        idPost: String,
        idUser: String
    ): ApiResultWrapper<RealEstateDetail> {
        return callApi {
            apiService.getPostDetailById(
                idPost = idPost,
                idUser = idUser
            )
        }
    }

    override suspend fun getPostSamePrice(
        idPost: String,
        idUser: String
    ): ApiResultWrapper<MutableList<RealEstateList>> {
        return callApi {
            apiService.getPostSamePrice(
                idPost = idPost,
                idUser = idUser
            )
        }
    }

    override suspend fun getPostSameCluster(
        idPost: String,
        idUser: String
    ): ApiResultWrapper<MutableList<RealEstateList>> {
        return callApi {
            apiService.getPostSameCluster(
                idPost = idPost,
                idUser = idUser
            )
        }
    }

    override suspend fun getDistricts(
        provinceId: String
    ): ApiResultWrapper<MutableList<ItemChoose>> {
        return callApi {
            apiService.getDistricts(
                provinceId = provinceId
            )
        }
    }

    override suspend fun getWards(
        districtId: String
    ): ApiResultWrapper<MutableList<ItemChoose>> {
        return callApi {
            apiService.getWards(
                districtId = districtId
            )
        }
    }

    override suspend fun getStreets(
        districtId: String,
        filter: String
    ): ApiResultWrapper<MutableList<ItemChoose>> {
        return callApi {
            apiService.getStreets(
                districtId = districtId,
                filter = filter
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
    ): ApiResultWrapper<PagingItem<RealEstateList>> {
        val options: MutableMap<String, Any?> = HashMap()
        options["minPrice"] = minPrice
        options["maxPrice"] = maxPrice
        options["minBedRoom"] = minBedRoom
        options["maxBedRoom"] = maxBedRoom
        options["minWidth"] = minWidth
        options["maxWidth"] = maxWidth
        options["minSquare"] = minSquare
        options["maxSquare"] = maxSquare
        options["minLength"] = minLength
        options["maxLength"] = maxLength
        options["minFloor"] = minFloor
        options["maxFloor"] = maxFloor
        options["minKitchen"] = minKitchen
        options["maxKitchen"] = maxKitchen
        options["propertyTypeId"] = propertyTypeId
        options["legalId"] = legalId
        options["carParking"] = carParking
        options["directionId"] = directionId
        options["rooftop"] = rooftop
        options["districtId"] = districtId
        options["wardId"] = wardId
        options["streetId"] = streetId
        options["minWidthRoad"] = minWidthRoad
        options["maxWidthRoad"] = maxWidthRoad
        options["pageIndex"] = pageIndex
        options["pageSize"] = pageSize
        options["search"] = search
        options["optionSort"] = optionSort
        return callApi {
            apiService.searchPostWithOptions(
                idUser = idUser,
                options = options
            )
        }
    }

    override suspend fun updateSavePost(
        idPost: Int,
        idUser: Int
    ): ApiResultWrapper<Any?> {
        val options: MutableMap<String, Any> = HashMap()
        options["postId"] = idPost
        options["userId"] = idUser
        return callApi {
            apiService.updateSavePost(options)
        }
    }

    override suspend fun getPostSaved(
        idUser: Int,
        pageIndex: Int,
        pageSize: Int,
        filter: String
    ): ApiResultWrapper<PagingItem<RealEstateList>> {
        return callApi {
            apiService.getPostSaved(
                idUser = idUser,
                pageIndex = pageIndex,
                pageSize = pageSize,
                filter = filter,
                createdDate = "desc"
            )
        }
    }

    override suspend fun getPostCreatedByUser(
        idUser: Int,
        pageIndex: Int,
        pageSize: Int,
        filter: String
    ): ApiResultWrapper<PagingItem<RealEstateList>> {
        return callApi {
            apiService.getPostCreatedByUser(
                idUser = idUser,
                pageIndex = pageIndex,
                pageSize = pageSize,
                filter = filter,
                createdDate = "desc"
            )
        }
    }

    override suspend fun uploadImage(
        image: File
    ): ApiResultWrapper<String> {
        return callApi {
            apiService.uploadImage(
                image = MultipartBody.Part.createFormData(
                    "img",
                    image.name,
                    image.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                )
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
    ): ApiResultWrapper<PredictResult> {
        val options: MutableMap<String, Any> = HashMap()
        options["bedRoom"] = bedRoom
        options["width"] = width
        options["acreage"] = acreage
        options["length"] = length
        options["floorNumber"] = floorNumber
        options["kitchen"] = kitchen
        options["diningRoom"] = diningRoom
        options["propertyTypeId"] = propertyTypeId
        options["legalTypeId"] = legalTypeId
        options["carParking"] = carParking
        options["directionId"] = directionId
        options["rooftop"] = rooftop
        options["districtId"] = districtId
        options["wardId"] = wardId
        options["streetId"] = streetId
        options["widthRoad"] = widthRoad
        return callApi {
            apiService.getPredictPrice(options)
        }
    }

    override suspend fun getComboOptions(): ApiResultWrapper<MutableList<ComboOption>> {
        return callApi {
            apiService.getComboOptions()
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
    ): ApiResultWrapper<Any?> {
        val options: MutableMap<String, Any> = HashMap()
        options["title"] = title
        options["description"] = description
        options["ownerId"] = ownerId
        options["price"] = price
        options["suggestedPrice"] = suggestedPrice
        options["directionId"] = directionId
        options["frontal"] = width
        options["acreage"] = acreage
        options["parkingSpace"] = parkingSpace
        options["streetInFront"] = streetInFront
        options["length"] = length
        options["bedroomNumber"] = bedroomNumber
        options["kitchen"] = kitchen
        options["rooftop"] = rooftop
        options["floorNumber"] = floorNumber
        options["diningRoom"] = diningRoom
        options["legalTypeId"] = legalTypeId
        options["isOwner"] = isOwner
        options["detail"] = detail
        options["provinceId"] = provinceId
        options["districtId"] = districtId
        options["wardId"] = wardId
        options["streetId"] = streetId
        options["longitude"] = longitude
        options["latitude"] = latitude
        options["images"] = images
        options["propertyTypeId"] = propertyTypeId
        options["cluster"] = cluster
        options["comboOptionId"] = comboOptionId
        return callApi {
            apiService.createPost(options)
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
    ): ApiResultWrapper<Any?> {
        val options: MutableMap<String, Any> = HashMap()
        options["title"] = title
        options["description"] = description
        options["price"] = price
        options["frontal"] = width
        options["acreage"] = acreage
        options["parkingSpace"] = parkingSpace
        options["streetInFront"] = streetInFront
        options["length"] = length
        options["bedroomNumber"] = bedroomNumber
        options["kitchen"] = kitchen
        options["rooftop"] = rooftop
        options["floorNumber"] = floorNumber
        options["diningRoom"] = diningRoom
        options["legalTypeId"] = legalTypeId
        options["detailAddress"] = detailAddress
        options["districtId"] = districtId
        options["wardId"] = wardId
        options["streetId"] = streetId
        options["longitude"] = longitude
        options["latitude"] = latitude
        options["listNewImages"] = listNewImages
        options["propertyTypeId"] = propertyTypeId
        options["comboOptionId"] = comboOptionId
        return callApi {
            apiService.updatePost(
                idPost = idPost.toString(),
                options = options
            )
        }
    }

    override suspend fun changePassWord(
        idUser: Int,
        oldPassword: String,
        newPassword: String
    ): ApiResultWrapper<Any?> {
        val options: MutableMap<String, Any> = HashMap()
        options["userId"] = idUser
        options["oldPassword"] = oldPassword
        options["newPassword"] = newPassword
        return callApi {
            apiService.changePassWord(
                options = options
            )
        }
    }
}
