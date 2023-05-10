package com.example.realestateapp.data.repository

import com.example.realestateapp.data.apiresult.ApiResultWrapper
import com.example.realestateapp.data.datasource.RetrofitDataSource
import com.example.realestateapp.data.models.User
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
    override suspend fun signIn(email: String, password: String): Flow<ApiResultWrapper<User?>> {
        return flow {
            emit(dataSource.signIn(email, password))
        }.onStart { emit(ApiResultWrapper.Loading) }
    }

    override suspend fun signUp(
        name: String,
        phone: String,
        email: String,
        password: String
    ): Flow<ApiResultWrapper<Boolean>> {
        return flow {
            emit(dataSource.signUp(name, phone, email, password))
        }.onStart { emit(ApiResultWrapper.Loading) }
    }
}
