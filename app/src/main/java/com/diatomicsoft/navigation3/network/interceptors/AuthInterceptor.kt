package com.diatomicsoft.navigation3.network.interceptors

import com.diatomicsoft.navigation3.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()

        // Add auth token if available
        // val token = tokenManager.getToken()
        // token?.let {
        //     request.addHeader("Authorization", "Bearer $it")
        // }

        // Add common headers
        request.addHeader("Accept", "application/json")
        request.addHeader("Content-Type", "application/json")
        request.addHeader("Platform", "Android")
        request.addHeader("App-Version", BuildConfig.VERSION_NAME)

        return chain.proceed(request.build())
    }
}