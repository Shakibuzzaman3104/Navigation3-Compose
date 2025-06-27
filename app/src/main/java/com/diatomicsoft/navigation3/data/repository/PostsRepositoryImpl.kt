package com.diatomicsoft.navigation3.data.repository

import com.diatomicsoft.navigation3.data.model.ModelPost
import com.diatomicsoft.navigation3.domain.repository.PostsRepository
import com.diatomicsoft.navigation3.local_storage.dao.PostDao
import com.diatomicsoft.navigation3.network.api.PostsApiService
import com.diatomicsoft.navigation3.network.resource.NetworkBoundResource
import com.diatomicsoft.navigation3.network.resource.Resource
import kotlinx.coroutines.flow.Flow

class PostsRepositoryImpl(
    private val api: PostsApiService,
    private val dao: PostDao,
) : PostsRepository {

    override suspend fun fetchPosts(): Flow<Resource<List<ModelPost>>> {
        return object : NetworkBoundResource<List<ModelPost>, List<ModelPost>>() {

            override suspend fun loadFromDb(): Flow<List<ModelPost>> {
                return dao.getAll()
            }

            override fun shouldFetch(data: List<ModelPost>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun fetchFromNetwork(): List<ModelPost> {
                val response = api.getPosts()
                if (response.isSuccessful) {
                    return response.body() ?: emptyList()
                } else {
                    throw retrofit2.HttpException(response)
                }
            }

            override suspend fun saveNetworkResult(item: List<ModelPost>) {
                dao.insertAll(item)
            }

            override fun onFetchFailed(throwable: Throwable) {
                throwable.printStackTrace()
            }
        }.asFlow()
    }

    override suspend fun fetchPost(postId: Int): Flow<Resource<ModelPost>> {
        return object : NetworkBoundResource<ModelPost, ModelPost>() {
            override suspend fun loadFromDb(): Flow<ModelPost> {
                return dao.getById(postId)
            }

            override suspend fun fetchFromNetwork(): ModelPost {
                val response = api.getPost(postId)
                if (response.isSuccessful) {
                    return response.body()!!
                } else {
                    throw retrofit2.HttpException(response)
                }
            }

            override suspend fun saveNetworkResult(item: ModelPost) {
                dao.insert(item)
            }

            override fun shouldFetch(data: ModelPost?): Boolean {
                return data == null || data.body.isEmpty()
            }
        }.asFlow()
    }

}

