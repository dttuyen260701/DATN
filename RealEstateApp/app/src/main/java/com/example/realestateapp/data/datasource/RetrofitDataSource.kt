package com.example.realestateapp.data.datasource

import com.example.realestateapp.data.apiresult.ApiResultWrapper
import com.example.realestateapp.data.models.User

/**
 * Created by tuyen.dang on 4/30/2023.
 */

interface RetrofitDataSource {
    suspend fun signIn(email: String, password: String): ApiResultWrapper<User?>

    suspend fun signUp(
        name: String,
        phone: String,
        email: String,
        password: String
    ): ApiResultWrapper<Boolean>
}
