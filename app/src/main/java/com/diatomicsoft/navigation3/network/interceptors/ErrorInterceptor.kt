package com.diatomicsoft.navigation3.network.interceptors

import com.diatomicsoft.navigation3.network.exceptions.NetworkException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            throw when (e) {
                is java.net.SocketTimeoutException -> NetworkException.TimeoutException()
                is java.io.IOException -> NetworkException.NoInternetException()
                else -> NetworkException.UnknownException(e.message ?: "Unknown error")
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