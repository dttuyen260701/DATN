package com.example.realestateapp.data.repository

import com.example.realestateapp.data.datasource.RetrofitDataSource
import javax.inject.Inject

/**
 * Created by tuyen.dang on 4/30/2023.
 */

class AppRepositoryImpl @Inject constructor(
    private val dataSource: RetrofitDataSource
) : AppRepository {

}
