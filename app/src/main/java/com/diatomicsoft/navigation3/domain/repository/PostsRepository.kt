package com.diatomicsoft.navigation3.domain.repository

import com.diatomicsoft.core.database.entity.ModelPost
import com.diatomicsoft.core.network.resource.Resource
import kotlinx.coroutines.flow.Flow

interface PostsRepository {

    suspend fun fetchPosts(): Flow<Resource<List<ModelPost>>>

    suspend fun fetchPost(postId: Int): Flow<Resource<ModelPost>>

}