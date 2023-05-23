package com.example.realestateapp.data.datasource

import com.example.realestateapp.data.apiresult.ApiResultWrapper
import com.example.realestateapp.data.models.*
import com.example.realestateapp.data.network.SafeAPI
import com.example.realestateapp.data.service.APIService
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
        filter: String
    ): ApiResultWrapper<MutableList<ItemChoose>> {
        return callApi {
            apiService.getStreets(
                filter = filter
            )
        }
    }
}
