package com.diatomicsoft.feature.album.data

import com.diatomicsoft.core.database.dao.AlbumDao
import com.diatomicsoft.core.database.entity.ModelAlbum
import com.diatomicsoft.core.network.api.AlbumsApiService
import com.diatomicsoft.core.network.resource.NetworkBoundResource
import com.diatomicsoft.core.network.resource.Resource
import com.diatomicsoft.feature.album.domain.AlbumsRepository
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