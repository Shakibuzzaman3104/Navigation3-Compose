package com.diatomicsoft.navigation3.data.repository

import com.diatomicsoft.navigation3.data.model.ModelComment
import com.diatomicsoft.navigation3.domain.repository.PostDetailsRepository
import com.diatomicsoft.navigation3.local_storage.dao.CommentDao
import com.diatomicsoft.navigation3.network.api.PostsApiService
import com.diatomicsoft.navigation3.network.resource.NetworkBoundResource
import com.diatomicsoft.navigation3.network.resource.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException

class PostDetailsRepositoryImpl(
    private val api: PostsApiService,
    private val dao: CommentDao,
) : PostDetailsRepository {

    override suspend fun fetchComments(postId: Int): Flow<Resource<List<ModelComment>>> {
        return object : NetworkBoundResource<List<ModelComment>, List<ModelComment>>() {

            override suspend fun loadFromDb(): Flow<List<ModelComment>> {
                return dao.getAll()
            }

            override fun shouldFetch(data: List<ModelComment>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun fetchFromNetwork(): List<ModelComment> {
                val response = api.getComments(postId)
                if (response.isSuccessful) {
                    return response.body() ?: emptyList()
                } else {
                    throw HttpException(response)
                }
            }

            override suspend fun saveNetworkResult(item: List<ModelComment>) {
                dao.insertAll(item)
            }

            override fun onFetchFailed(throwable: Throwable) {
                throwable.printStackTrace()
            }
        }.asFlow()
    }

    override suspend fun fetchComment(commentId: Int): Flow<Resource<ModelComment>> {
        return object : NetworkBoundResource<ModelComment, ModelComment>() {
            override suspend fun loadFromDb(): Flow<ModelComment> {
                return dao.getById(commentId)
            }

            override suspend fun fetchFromNetwork(): ModelComment {
                val response = api.getComment(commentId)
                if (response.isSuccessful) {
                    return response.body()!!
                } else {
                    throw HttpException(response)
                }
            }

            override suspend fun saveNetworkResult(item: ModelComment) {
                dao.insert(item)
            }

            override fun shouldFetch(data: ModelComment?): Boolean {
                return data == null || data.body.isEmpty()
            }
        }.asFlow()
    }

}

