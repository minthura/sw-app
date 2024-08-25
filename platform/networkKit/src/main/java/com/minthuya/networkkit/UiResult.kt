package com.minthuya.networkkit

sealed class UiResult<T>(val body: T? = null, val error: com.minthuya.networkkit.Error? = null) {
    class Success<T>(body: T) : UiResult<T>(body)
    class Error<T>(error: com.minthuya.networkkit.Error?, body: T? = null) : UiResult<T>(
        body,
        error
    )
    class Loading<T> : UiResult<T>()
}