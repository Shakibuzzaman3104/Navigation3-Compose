package com.diatomicsoft.navigation3.network.interceptors

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.diatomicsoft.navigation3.network.utils.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkConnectionInterceptor @Inject constructor(
    private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        val isConnected = capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        if (!isConnected) {
            throw NoConnectivityException()
        }

        val request = chain.request().newBuilder().build()
        // Optionally, add headers here
        // val newRequest = request.newBuilder()
        //     .addHeader("Authorization", "Bearer your_token")
        //     .build()
        // return chain.proceed(newRequest)

        return chain.proceed(request)
    }
}