package com.minthuya.network.di

import com.minthuya.component.AppScope
import com.minthuya.network.NetworkConfig
import com.minthuya.network.NetworkKitImpl
import com.minthuya.networkkit.NetworkKit
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    @Provides
    @AppScope
    fun provideNetworkKit(
        retrofit: Retrofit
    ): NetworkKit = NetworkKitImpl(
        retrofit
    )

    @Provides
    @AppScope
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