package com.minthuya.networkkit

sealed class ApiResult<T>(val body: T? = null, val errorCode: String? = null, val message: String? = null) {
    class Success<T>(body: T) : ApiResult<T>(body)
    class Error<T>(errorMessage: String? = null, errorCode: String? = null, body: T? = null) : ApiResult<T>(
        body,
        errorCode,
        errorMessage
    )
}