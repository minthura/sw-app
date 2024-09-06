package com.minthuya.sw.data.api

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming

interface SWApi {
    @Streaming
    @GET("pc/ad/nxa24.zip")
    suspend fun downloadNxa24Zip(): ResponseBody
}