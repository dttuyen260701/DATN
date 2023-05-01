package com.example.realestateapp.data.di

import com.example.realestateapp.data.datasource.RetrofitDataSource
import com.example.realestateapp.data.datasource.RetrofitDataSourceImpl
import com.example.realestateapp.data.repository.AppRepository
import com.example.realestateapp.data.repository.AppRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by tuyen.dang on 4/30/2023.
 */

@Module
@InstallIn(SingletonComponent::class)
interface ApiSourceModule {
    @Binds
    fun bindRetrofitDataSource(impl: RetrofitDataSourceImpl): RetrofitDataSource

    @Binds
    fun bindAppRepository(impl: AppRepositoryImpl): AppRepository
}
