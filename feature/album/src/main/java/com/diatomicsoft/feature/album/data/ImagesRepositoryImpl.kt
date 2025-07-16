package com.diatomicsoft.feature.album.data

import com.diatomicsoft.core.database.dao.PhotoDao
import com.diatomicsoft.core.database.entity.ModelPhoto
import com.diatomicsoft.core.network.api.AlbumsApiService
import com.diatomicsoft.core.network.resource.NetworkBoundResource
import com.diatomicsoft.core.network.resource.Resource
import com.diatomicsoft.feature.album.domain.ImagesRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class ImagesRepositoryImpl @Inject constructor(
    private val api: AlbumsApiService,
    private val dao: PhotoDao
) : ImagesRepository {

    override fun fetchImages(albumId: Int): Flow<Resource<List<ModelPhoto>>> {
        return object : NetworkBoundResource<List<ModelPhoto>, List<ModelPhoto>>() {

            override suspend fun loadFromDb(): Flow<List<ModelPhoto>> {
                return dao.getByAlbumId(albumId)
            }

            override fun shouldFetch(data: List<ModelPhoto>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun fetchFromNetwork(): List<ModelPhoto> {
                val response = api.getPhotos(albumId)
                if (response.isSuccessful) {
                    return response.body() ?: emptyList()
                } else {
                    throw HttpException(response)
                }
            }

            override suspend fun saveNetworkResult(item: List<ModelPhoto>) {
                dao.insertAll(item)
            }

        }.asFlow()
    }
}