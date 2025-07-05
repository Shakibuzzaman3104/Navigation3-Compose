package com.diatomicsoft.navigation3.data.repository

import com.diatomicsoft.navigation3.data.model.ModelAlbum
import com.diatomicsoft.navigation3.domain.repository.AlbumsRepository
import com.diatomicsoft.navigation3.local_storage.dao.AlbumDao
import com.diatomicsoft.navigation3.network.api.AlbumsApiService
import com.diatomicsoft.navigation3.network.resource.NetworkBoundResource
import com.diatomicsoft.navigation3.network.resource.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class AlbumsRepositoryImpl(
    private val api: AlbumsApiService,
    private val dao: AlbumDao,
) : AlbumsRepository {

    override suspend fun fetchAlbums(): Flow<Resource<List<ModelAlbum>>> {
        return object : NetworkBoundResource<List<ModelAlbum>, List<ModelAlbum>>() {

            override suspend fun loadFromDb(): Flow<List<ModelAlbum>> {
                return dao.getAll()
            }

            override fun shouldFetch(data: List<ModelAlbum>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun fetchFromNetwork(): List<ModelAlbum> {
                val response = api.getAlbums()
                if (response.isSuccessful) {
                    return response.body() ?: emptyList()
                } else {
                    throw HttpException(response)
                }
            }

            override suspend fun saveNetworkResult(item: List<ModelAlbum>) {
                dao.insertAll(item)
            }

            override fun onFetchFailed(throwable: Throwable) {
                throwable.printStackTrace()
            }
        }.asFlow()
    }

}