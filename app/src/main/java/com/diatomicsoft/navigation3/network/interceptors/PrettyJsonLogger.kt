package com.diatomicsoft.navigation3.network.interceptors

import com.squareup.moshi.Moshi
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

class PrettyJsonLogger(private val moshi: Moshi) : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        val logName = "OkHttp"
        if (message.startsWith("{") || message.startsWith("[")) {
            try {
                // Parse and re-format JSON for pretty printing
                val prettyPrint = if (message.startsWith("{")) {
                    JSONObject(message).toString(4)
                } else {
                    JSONArray(message).toString(4)
                }
                Timber.tag(logName).d(prettyPrint)
            } catch (e: Exception) {
                // Fallback for malformed JSON
                Timber.tag(logName).d(message)
            }
        } else {
            Timber.tag(logName).d(message)
        }
    }
}