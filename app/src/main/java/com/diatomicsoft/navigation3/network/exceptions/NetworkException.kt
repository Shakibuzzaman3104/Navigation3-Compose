package com.diatomicsoft.navigation3.network.exceptions

sealed class NetworkException(message: String) : Exception(message) {
    class NoInternetException : NetworkException("No internet connection available")
    class ServerException(val code: Int, message: String) : NetworkException("Server error: $code - $message")
    class UnknownException(message: String) : NetworkException("Unknown error: $message")
    class TimeoutException : NetworkException("Request timed out")
    class ParseException : NetworkException("Error parsing response")
    class UnauthorizedException : NetworkException("Unauthorized access")
    class ForbiddenException : NetworkException("Access forbidden")
}
