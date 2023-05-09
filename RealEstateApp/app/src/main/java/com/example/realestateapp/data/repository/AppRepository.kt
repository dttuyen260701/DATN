package com.example.realestateapp.data.repository

import com.example.realestateapp.data.apiresult.ApiResultWrapper
import com.example.realestateapp.data.models.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by tuyen.dang on 4/30/2023.
 */

interface AppRepository {
    suspend fun login(email: String, password: String): Flow<ApiResultWrapper<User?>>
}
