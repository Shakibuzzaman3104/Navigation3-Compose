package com.diatomicsoft.navigation3.domain.repository

import com.diatomicsoft.navigation3.data.model.ModelPost
import kotlinx.coroutines.flow.Flow

interface PostsRepository {

    suspend fun fetchPosts(): Flow<List<ModelPost>>

}