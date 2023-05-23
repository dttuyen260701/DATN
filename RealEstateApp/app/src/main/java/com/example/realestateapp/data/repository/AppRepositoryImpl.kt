package com.example.realestateapp.data.repository

import com.example.realestateapp.data.apiresult.ApiResultWrapper
import com.example.realestateapp.data.datasource.RetrofitDataSource
import com.example.realestateapp.data.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
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
        filter: String,
        showLoading: Boolean
    ): Flow<ApiResultWrapper<MutableList<ItemChoose>>> {
        return flow {
            emit(
                dataSource.getStreets(filter)
            )
        }.onStart { if (showLoading) emit(ApiResultWrapper.Loading) }
    }
}
