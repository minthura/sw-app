package com.minthuya.networkkit

import retrofit2.HttpException
import retrofit2.Response

fun <T> Response<T>.safeResponse(): T {
    if (isSuccessful) {
        body()?.let {
            return it
        }
    }
    throw HttpException(this)
}