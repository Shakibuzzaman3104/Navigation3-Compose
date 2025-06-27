package com.diatomicsoft.navigation3.domain.repository

import com.diatomicsoft.navigation3.data.model.ModelPost
import com.diatomicsoft.navigation3.network.resource.Resource
import kotlinx.coroutines.flow.Flow

interface PostsRepository {

    suspend fun fetchPosts(): Flow<Resource<List<ModelPost>>>

    suspend fun fetchPost(postId: Int): Flow<Resource<ModelPost>>

}