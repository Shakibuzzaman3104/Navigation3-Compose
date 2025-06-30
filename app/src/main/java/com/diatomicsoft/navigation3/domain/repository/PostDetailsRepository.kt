package com.diatomicsoft.navigation3.domain.repository

import com.diatomicsoft.navigation3.data.model.ModelComment
import com.diatomicsoft.navigation3.network.resource.Resource
import kotlinx.coroutines.flow.Flow

interface PostDetailsRepository {

    suspend fun fetchComments(postId: Int): Flow<Resource<List<ModelComment>>>

    suspend fun fetchComment(commentId: Int): Flow<Resource<ModelComment>>

}