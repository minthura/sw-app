package com.minthuya.networkkit

interface NetworkKit {
    fun <T> createService(service: Class<T>): T
}