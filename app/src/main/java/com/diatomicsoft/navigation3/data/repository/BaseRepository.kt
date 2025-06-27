package com.diatomicsoft.navigation3.data.repository

import com.diatomicsoft.navigation3.network.exceptions.NetworkException
import com.diatomicsoft.navigation3.network.resource.Resource
import com.diatomicsoft.navigation3.network.utils.NetworkMonitor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.withTimeoutOrNull
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject

abstract class BaseRepository {

    @Inject
    lateinit var connectivityManager: NetworkMonitor

    protected fun <T : Any> safeApiCall( // Added ': Any' constraint here
        retryCount: Long = 3,
        retryDelayMillis: Long = 1000,
        timeoutMillis: Long = 30000,
        apiCall: suspend () -> T
    ): Flow<Resource<T>> = flow {
        if (!connectivityManager.isOnline()) {
            emit(Resource.Error(NetworkException.NoInternetException().message ?: "No internet"))
            return@flow
        }

        val result: T? = withTimeoutOrNull(timeoutMillis) {
            apiCall()
        }

        if (result != null) {
            emit(Resource.Success(result))
        } else {
            emit(Resource.Error(NetworkException.TimeoutException().message ?: "Timeout"))
        }
    }
        .retry(retries = retryCount) { cause ->
            if (cause is IOException || cause is SocketTimeoutException) {
                delay(retryDelayMillis)
                true
            } else {
                false
            }
        }
        .onStart { emit(Resource.Loading()) }
        .catch { exception ->
            emit(Resource.Error(handleException(exception).message ?: "Unknown error"))
        }
        .flowOn(Dispatchers.IO)

    private fun handleException(exception: Throwable): NetworkException {
        return when (exception) {
            is NetworkException -> exception
            is IOException -> NetworkException.NoInternetException()
            is SocketTimeoutException -> NetworkException.TimeoutException()
            is retrofit2.HttpException -> {
                val code = exception.code()
                val message = exception.response()?.errorBody()?.string() ?: "Unknown error"
                NetworkException.ServerException(code, message)
            }

            else -> NetworkException.UnknownException(exception.message ?: "Unknown error")
        }
    }
}