package com.minthuya.networkkit

data class Error(
    val type: ErrorType,
    val code: String?,
    val message: String?,
)

enum class ErrorType {
    ERROR_NO_INTERNET,
    ERROR_HTTP,
    ERROR_UNKNOWN
}