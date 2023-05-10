package com.example.realestateapp.data.di

import com.example.realestateapp.BuildConfig
import com.example.realestateapp.data.service.APIService
import com.example.realestateapp.util.AuthenticationObject
import com.example.realestateapp.util.Constants
import com.example.realestateapp.util.Constants.HeaderRequest
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by tuyen.dang on 4/30/2023.
 */

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideBaseURL() = BuildConfig.API_URL

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

    @Provides
    @Singleton
    fun provideOKHttpClient(): OkHttpClient {
        val requestInterceptor = Interceptor { chain ->
            val url = chain.request()
                .url
                .newBuilder()
                .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .addHeader(HeaderRequest.CONTENT_TYPE, HeaderRequest.CONTENT_TYPE_VALUE)
                .addHeader(HeaderRequest.AUTHORIZATION, "Bearer ${AuthenticationObject.token}")
                .build()

            return@Interceptor chain.proceed(request)
        }

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return if (BuildConfig.DEBUG) {
            OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(requestInterceptor)
                .connectTimeout(Constants.DefaultConfig.NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.DefaultConfig.NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.DefaultConfig.NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .build()
        } else {
            OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .connectTimeout(Constants.DefaultConfig.NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.DefaultConfig.NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.DefaultConfig.NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .build()
        }
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, gson: Gson, client: OkHttpClient): APIService =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService::class.java)
}
