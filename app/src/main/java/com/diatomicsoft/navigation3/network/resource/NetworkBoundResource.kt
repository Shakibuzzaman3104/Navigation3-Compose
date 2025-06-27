package com.diatomicsoft.navigation3.network.resource

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.Dispatchers

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
                onFetchFailed(throwable)
                emitAll(loadFromDb().map {
                    Resource.Error(throwable.message ?: "Unknown error", it)
                })
            }
        } else {
            emitAll(loadFromDb().map { Resource.Success(it) })
        }
    }.flowOn(Dispatchers.IO)

    protected abstract suspend fun loadFromDb(): Flow<ResultType>
    protected abstract suspend fun fetchFromNetwork(): RequestType
    protected abstract suspend fun saveNetworkResult(item: RequestType)
    protected abstract fun shouldFetch(data: ResultType?): Boolean
    protected open fun onFetchFailed(throwable: Throwable) {}
}