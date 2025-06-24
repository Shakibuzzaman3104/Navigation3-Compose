package com.diatomicsoft.navigation3.domain.repository

import com.diatomicsoft.navigation3.data.model.Post
import kotlinx.coroutines.flow.StateFlow

interface PostsRepository {

    suspend fun fetchPosts(): StateFlow<List<Post>>

}