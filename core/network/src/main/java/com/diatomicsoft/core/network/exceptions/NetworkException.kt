package com.diatomicsoft.core.network.exceptions

import java.io.IOException

sealed class NetworkException(message: String) : IOException(message) {
    class NoInternetException : NetworkException("No internet connection available")
    class ServerException(val code: Int, message: String) : NetworkException("Server error: $code - $message")
    class UnknownException(message: String) : NetworkException("Unknown error: $message")
    class TimeoutException : NetworkException("Request timed out")
    class ParseException : NetworkException("Error parsing response")
    class UnauthorizedException : NetworkException("Unauthorized access")
    class ForbiddenException : NetworkException("Access forbidden")
}