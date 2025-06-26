package com.diatomicsoft.navigation3.network.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.sync.Semaphore
import retrofit2.HttpException
import timber.log.Timber
import com.diatomicsoft.navigation3.network.Result

private val networkSemaphore = Semaphore(permits = 3)

fun <T> safeApiCall(apiCall: suspend () -> retrofit2.Response<T>): Flow<Result<T>> = flow {
    // Acquire a permit. This will suspend if the semaphore is out of permits.
    networkSemaphore.acquire()
    Timber.d("Semaphore acquired. Current available permits: ${networkSemaphore.availablePermits}")

    val response = apiCall()
    if (response.isSuccessful) {
        val body = response.body()
        if (body != null) {
            emit(Result.Success(body))
        } else {
            emit(Result.Error("API call successful but body was null.", response.code()))
        }
    } else {
        val errorBody = response.errorBody()?.string() ?: "Unknown error"
        Timber.e("API Error: ${response.code()} - $errorBody")
        if (response.code() in 500..599) {
            throw RecoverableServerException("Server error: ${response.code()}")
        } else {
            emit(Result.Error("API call failed: $errorBody", response.code()))
        }
    }
}.onStart {
    emit(Result.Loading)
}.catch { e ->
    Timber.e(e, "API call failed with exception")
    when (e) {
        is NoConnectivityException -> emit(Result.Error(e.message ?: "No Internet Connection"))
        is HttpException -> emit(Result.Error("Network error: ${e.message()}", e.code()))
        is RecoverableServerException -> throw e // Re-throw to be caught by retryWhen
        else -> emit(Result.Error("An unexpected error occurred: ${e.message}"))
    }
}.retryWhen { cause, attempt ->
    if (cause is RecoverableServerException && attempt < 3) {
        val delayMillis = 1000L * (attempt + 1) // Exponential backoff: 1s, 2s, 3s
        Timber.w("Retrying API call... Attempt #${attempt + 1}, Delaying for ${delayMillis}ms")
        delay(delayMillis)
        true // Return true to retry
    } else {
        if (attempt >= 3) {
            emit(Result.Error("Request failed after multiple retries for: ${cause.message}", null))
        }
        false // Stop retrying
    }
}.catch { e ->
    emit(Result.Error("Failed to fetch data: ${e.message}", null))
}.onCompletion {
    networkSemaphore.release()
    Timber.d("Semaphore released. Current available permits: ${networkSemaphore.availablePermits}")
}.flowOn(Dispatchers.IO)