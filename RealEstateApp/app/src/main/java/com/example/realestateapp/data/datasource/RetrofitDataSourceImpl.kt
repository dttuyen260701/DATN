package com.example.realestateapp.data.datasource

import com.example.realestateapp.data.apiresult.ApiResultWrapper
import com.example.realestateapp.data.models.ItemChoose
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
}
