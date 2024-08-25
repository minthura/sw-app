package com.minthuya.networkkit

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> Flow<T>.execute(success: ((T) -> Unit), error: ((error: Error) -> Unit)) {
    catch { t ->
        when (t) {
            is IOException -> error(Error(ErrorType.ERROR_NO_INTERNET, null, t.message))
            is HttpException -> error(Error(ErrorType.ERROR_HTTP, t.code().toString(), t.message))
            else -> error(Error(ErrorType.ERROR_UNKNOWN, null, t.message))
        }
    }.collect {
        success(it)
    }
}

fun <T> MutableSharedFlow<T>.postEmit(t: T) {
    runBlocking {
        emit(t)
    }
}