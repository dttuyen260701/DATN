package com.example.realestateapp.data.datasource

import com.example.realestateapp.data.network.SafeAPI
import com.example.realestateapp.data.service.APIService
import javax.inject.Inject

/**
 * Created by tuyen.dang on 4/30/2023.
 */

class RetrofitDataSourceImpl @Inject constructor(
    private val apiService: APIService
) : RetrofitDataSource, SafeAPI() {

}
