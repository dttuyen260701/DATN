package com.example.realestateapp.data.datasource

import com.example.realestateapp.data.apiresult.ApiResultWrapper
import com.example.realestateapp.data.models.ItemChoose
import com.example.realestateapp.data.models.PagingItem
import com.example.realestateapp.data.models.RealEstateList
import com.example.realestateapp.data.models.User

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
}
