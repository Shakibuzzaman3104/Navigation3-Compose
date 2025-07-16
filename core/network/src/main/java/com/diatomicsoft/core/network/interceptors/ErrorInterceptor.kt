package com.diatomicsoft.core.network.interceptors

import com.diatomicsoft.core.network.exceptions.NetworkException
import com.diatomicsoft.core.network.utils.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorInterceptor @Inject constructor(
    private val networkUtils: NetworkUtils
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkUtils.isNetworkAvailable()) {
            throw NetworkException.NoInternetException()
        }

        val request = chain.request()
        val response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            // Handle specific IOException types more granularly
            when (e) {
                is java.util.concurrent.CancellationException -> {
                    // Re-throw cancellation exceptions as-is to allow proper flow cancellation
                    throw e
                }
                is java.net.SocketTimeoutException -> throw NetworkException.TimeoutException()
                is java.net.UnknownHostException -> throw NetworkException.NoInternetException()
                is java.net.ConnectException -> throw NetworkException.NoInternetException()
                is java.io.IOException -> {
                    // Check if it's a cancellation disguised as IOException
                    if (e.message?.contains("Canceled", ignoreCase = true) == true) {
                        // Re-throw the original IOException for cancellations
                        throw e
                    }
                    // Check actual network connectivity
                    if (!networkUtils.isNetworkAvailable()) {
                        throw NetworkException.NoInternetException()
                    } else {
                        throw NetworkException.UnknownException(e.message ?: "Network error")
                    }
                }
                else -> throw NetworkException.UnknownException(e.message ?: "Unknown error")
            }
        }

        when (response.code) {
            401 -> throw NetworkException.UnauthorizedException()
            403 -> throw NetworkException.ForbiddenException()
            in 400..499 -> {
                val errorBody = response.body?.string() ?: "Client error"
                throw NetworkException.ServerException(response.code, errorBody)
            }
            in 500..599 -> {
                val errorBody = response.body?.string() ?: "Server error"
                throw NetworkException.ServerException(response.code, errorBody)
            }
        }

        return response
    }
}