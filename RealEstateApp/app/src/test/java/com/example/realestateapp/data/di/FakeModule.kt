package com.example.realestateapp.data.di

import com.example.realestateapp.data.datasource.RetrofitDataSource
import com.example.realestateapp.data.datasource.RetrofitDataSourceImpl
import com.example.realestateapp.data.fake.FakeAppRepository
import com.example.realestateapp.data.repository.AppRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ApiSourceModule::class]
)
interface FakeModule {
    @Binds
    fun bindRetrofitDataSource(impl: RetrofitDataSourceImpl): RetrofitDataSource

    @Binds
    fun bindAppRepository(impl: FakeAppRepository): AppRepository
}