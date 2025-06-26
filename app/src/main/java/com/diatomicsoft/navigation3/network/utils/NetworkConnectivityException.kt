package com.diatomicsoft.navigation3.network.utils

import java.io.IOException

class NoConnectivityException : IOException("No network available, please check your WiFi or Data connection")

class RecoverableServerException(message: String) : IOException(message)
