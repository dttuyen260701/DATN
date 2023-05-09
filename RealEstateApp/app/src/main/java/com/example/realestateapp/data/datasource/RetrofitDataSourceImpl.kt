package com.example.realestateapp.data.datasource

import com.example.realestateapp.data.apiresult.ApiResultWrapper
import com.example.realestateapp.data.models.User
import com.example.realestateapp.data.network.SafeAPI
import com.example.realestateapp.data.service.APIService
import javax.inject.Inject

/**
 * Created by tuyen.dang on 4/30/2023.
 */

class RetrofitDataSourceImpl @Inject constructor(
    private val apiService: APIService
) : RetrofitDataSource, SafeAPI() {

    override suspend fun login(email: String, password: String): ApiResultWrapper<User?> {
        val options: MutableMap<String, String> = HashMap()
        options["userName"] = email
        options["password"] = password
        return callApi {
            apiService.login(options)
        }
    }


}
