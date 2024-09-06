package com.minthuya.network.di

import com.minthuya.component.AppScope
import com.minthuya.network.NetworkConfig
import com.minthuya.network.NetworkConstant
import com.minthuya.network.NetworkKitImpl
import com.minthuya.networkkit.NetworkKit
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
class NetworkModule {

    @Provides
    @AppScope
    fun provideNetworkKit(
        @Named(NetworkConstant.BASE_RETROFIT) retrofit: Retrofit,
        @Named(NetworkConstant.SW_RETROFIT) swRetrofit: Retrofit
    ): NetworkKit = NetworkKitImpl(
        retrofit,
        swRetrofit
    )

    @Provides
    @AppScope
    @Named(NetworkConstant.BASE_RETROFIT)
    fun provideServiceRetrofit(
        networkConfig: NetworkConfig,
        okHttpClient: OkHttpClient
    ) = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(networkConfig.baseUrl)
        .build()

    @Provides
    @AppScope
    @Named(NetworkConstant.SW_RETROFIT)
    fun provideSwServiceRetrofit(
        networkConfig: NetworkConfig,
        okHttpClient: OkHttpClient
    ) = Retrofit.Builder()
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(networkConfig.swBaseUrl)
        .build()

    @Provides
    @AppScope
    fun provideOkHttp(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder().addInterceptor(
        httpLoggingInterceptor
    ).build()

    @Provides
    @AppScope
    fun provideHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
}