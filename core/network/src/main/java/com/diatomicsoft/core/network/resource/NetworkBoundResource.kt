package com.diatomicsoft.core.network.resource

import com.diatomicsoft.core.network.exceptions.NetworkException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

abstract class NetworkBoundResource<ResultType, RequestType> {

    fun asFlow(): Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())

        val dbData = loadFromDb().first()
        emit(Resource.Loading(dbData))

        if (shouldFetch(dbData)) {
            emit(Resource.Loading(dbData))

            try {
                val apiResponse = fetchFromNetwork()
                saveNetworkResult(apiResponse)
                emitAll(loadFromDb().map { Resource.Success(it) })
            } catch (throwable: Throwable) {
                Timber.e(throwable, "NetworkBoundResource: Exception caught in fetchFromNetwork")
                // Handle cancellation exceptions by rethrowing them
                when (throwable) {
                    is java.util.concurrent.CancellationException -> {
                        Timber.d("NetworkBoundResource: Request was cancelled")
                        throw throwable
                    }

                    is java.io.IOException -> {
                        // Check if it's a cancellation disguised as IOException
                        if (throwable.message?.contains("Canceled", ignoreCase = true) == true) {
                            Timber.d("NetworkBoundResource: Request was cancelled (IOException)")
                            throw throwable
                        }
                        // Handle other IOExceptions as errors
                        Timber.e(throwable, "NetworkBoundResource: Network fetch failed")

                        emitAll(loadFromDb().map {
                            Resource.Error(throwable.message ?: "Network error occurred", it)
                        })
                    }

                    else -> {
                        // Log the error for debugging
                        Timber.e(throwable, "NetworkBoundResource: Network fetch failed")

                        // Emit error with cached data if available
                        emitAll(loadFromDb().map {
                            Resource.Error(throwable.message ?: "Network error occurred", it)
                        })
                    }
                }
            }
        } else {
            emitAll(loadFromDb().map { Resource.Success(it) })
        }
    }.catch { throwable ->
        // Handle any uncaught exceptions in the flow
        when (throwable) {
            is NetworkException -> {
                // NetworkException with specific message
                emit(Resource.Error(throwable.message ?: "Network error occurred"))
            }
            is java.util.concurrent.CancellationException -> {
                // Re-throw cancellation exceptions
                throw throwable
            }
            else -> {
                // Generic error handling
                Timber.e(throwable, "NetworkBoundResource: Uncaught exception in flow")
                emit(Resource.Error(throwable.message ?: "An unexpected error occurred"))
            }
        }
    }.flowOn(Dispatchers.IO)

    protected abstract suspend fun loadFromDb(): Flow<ResultType>
    protected abstract suspend fun fetchFromNetwork(): RequestType
    protected abstract suspend fun saveNetworkResult(item: RequestType)
    protected abstract fun shouldFetch(data: ResultType?): Boolean
}