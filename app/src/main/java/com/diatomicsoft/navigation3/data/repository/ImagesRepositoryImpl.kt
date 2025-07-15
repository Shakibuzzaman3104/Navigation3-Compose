package com.diatomicsoft.navigation3.data.repository

import com.diatomicsoft.core.database.entity.ModelPhoto
import com.diatomicsoft.core.database.dao.PhotoDao
import com.diatomicsoft.navigation3.domain.repository.ImagesRepository
import com.diatomicsoft.core.network.api.AlbumsApiService
import com.diatomicsoft.core.network.resource.NetworkBoundResource
import com.diatomicsoft.core.network.resource.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class ImagesRepositoryImpl(
    private val api: AlbumsApiService,
    private val dao: PhotoDao,
) : ImagesRepository {

    override suspend fun fetchImages(id: Int): Flow<Resource<List<ModelPhoto>>> {
        return object : NetworkBoundResource<List<ModelPhoto>, List<ModelPhoto>>() {

            override suspend fun loadFromDb(): Flow<List<ModelPhoto>> {
                return dao.getAll()
            }

            override fun shouldFetch(data: List<ModelPhoto>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun fetchFromNetwork(): List<ModelPhoto> {
                val response = api.getPhotos(id)
                if (response.isSuccessful) {
                    return response.body() ?: emptyList()
                } else {
                    throw HttpException(response)
                }
            }

            override suspend fun saveNetworkResult(item: List<ModelPhoto>) {
                dao.insertAll(item)
            }

            override fun onFetchFailed(throwable: Throwable) {
                throwable.printStackTrace()
            }
        }.asFlow()
    }

}