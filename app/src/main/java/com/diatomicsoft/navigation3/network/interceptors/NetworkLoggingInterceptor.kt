package com.diatomicsoft.navigation3.network.interceptors

import android.util.Log
import com.diatomicsoft.navigation3.BuildConfig
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkLoggingInterceptor @Inject constructor() {
    fun create(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            val tag = "NetworkModule"
            when {
                message.startsWith("--> ") -> {
                    Log.d(tag, "╔════════════════════════════════════════════════════════")
                    Log.d(tag, "║ REQUEST: $message")
                }
                message.startsWith("<-- ") -> {
                    Log.d(tag, "║ RESPONSE: $message")
                    Log.d(tag, "╚════════════════════════════════════════════════════════")
                }
                message.startsWith("{") || message.startsWith("[") -> {
                    // Pretty print JSON
                    try {
                        val prettyJson = formatJson(message)
                        prettyJson.lines().forEach { line ->
                            Log.d(tag, "║ $line")
                        }
                    } catch (e: Exception) {
                        Log.d(tag, "║ $message")
                    }
                }
                else -> Log.d(tag, "║ $message")
            }
        }.apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    private fun formatJson(json: String): String {
        val indentSize = 2
        var indent = 0
        val formatted = StringBuilder()
        var inQuotes = false
        var escape = false

        for (char in json) {
            when (char) {
                '"' -> {
                    if (!escape) inQuotes = !inQuotes
                    formatted.append(char)
                    escape = false
                }
                '\\' -> {
                    formatted.append(char)
                    escape = !escape
                }
                '{', '[' -> {
                    formatted.append(char)
                    if (!inQuotes) {
                        formatted.append("\n")
                        indent += indentSize
                        formatted.append(" ".repeat(indent))
                    }
                }
                '}', ']' -> {
                    if (!inQuotes) {
                        formatted.append("\n")
                        indent -= indentSize
                        formatted.append(" ".repeat(indent))
                    }
                    formatted.append(char)
                }
                ',' -> {
                    formatted.append(char)
                    if (!inQuotes) {
                        formatted.append("\n")
                        formatted.append(" ".repeat(indent))
                    }
                }
                ':' -> {
                    formatted.append(char)
                    if (!inQuotes) formatted.append(" ")
                }
                else -> {
                    if (char != ' ' || inQuotes) {
                        formatted.append(char)
                    }
                }
            }
        }

        return formatted.toString()
    }
}