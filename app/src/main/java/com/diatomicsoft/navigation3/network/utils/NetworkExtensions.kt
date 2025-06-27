package com.diatomicsoft.navigation3.network.utils

import com.diatomicsoft.navigation3.network.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

fun <T> Response<T>.toResource(): Resource<T> {
    return try {
        if (isSuccessful) {
            body()?.let {
                Resource.Success(it)
            } ?: Resource.Error("Empty response body")
        } else {
            Resource.Error("Error: ${code()} ${message()}")
        }
    } catch (e: Exception) {
        Resource.Error(e.message ?: "Unknown error")
    }
}

fun <T> Flow<T>.asResource(): Flow<Resource<T>> = flow {
    emit(Resource.Loading())
    try {
        collect { value ->
            emit(Resource.Success(value))
        }
    } catch (e: Exception) {
        emit(Resource.Error(e.message ?: "Unknown error"))
    }
}
