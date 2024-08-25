package com.minthuya.sample.data.api

import com.minthuya.sample.data.dtos.Weather
import retrofit2.Response
import retrofit2.http.GET

interface SampleApi {
    @GET("/data/2.5/weather?lat=1.3353144&lon=103.9492164&appid=5671e4ad8572fd2701d2def634a1e561")
    suspend fun getWeatherData(): Response<Weather>
}