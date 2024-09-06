package com.minthuya.network

import com.minthuya.networkkit.NetworkKit
import retrofit2.Retrofit

class NetworkKitImpl(
    private val msRetrofit: Retrofit,
    private val swRetrofit: Retrofit
) : NetworkKit {
    override fun <T> createService(service: Class<T>): T = msRetrofit.create(service)
    override fun <T> createSWService(service: Class<T>): T = swRetrofit.create(service)
}