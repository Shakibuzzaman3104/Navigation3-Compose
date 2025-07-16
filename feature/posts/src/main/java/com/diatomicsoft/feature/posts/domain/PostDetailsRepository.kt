package com.diatomicsoft.feature.posts.domain

import com.diatomicsoft.core.database.entity.ModelComment
import com.diatomicsoft.core.network.resource.Resource
import kotlinx.coroutines.flow.Flow

interface PostDetailsRepository {

    suspend fun fetchComments(postId: Int): Flow<Resource<List<ModelComment>>>

    suspend fun fetchComment(commentId: Int): Flow<Resource<ModelComment>>

}