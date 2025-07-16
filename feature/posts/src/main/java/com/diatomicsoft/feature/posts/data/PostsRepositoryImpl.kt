package com.diatomicsoft.feature.posts.data

import com.diatomicsoft.core.database.dao.PostDao
import com.diatomicsoft.core.database.entity.ModelPost
import com.diatomicsoft.core.network.api.PostsApiService
import com.diatomicsoft.core.network.resource.NetworkBoundResource
import com.diatomicsoft.core.network.resource.Resource
import com.diatomicsoft.feature.posts.domain.PostsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

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
                    throw HttpException(response)
                }
            }

            override suspend fun saveNetworkResult(item: List<ModelPost>) {
                dao.insertAll(item)
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
                    throw HttpException(response)
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