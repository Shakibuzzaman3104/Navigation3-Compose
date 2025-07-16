package com.diatomicsoft.feature.posts.ui

import com.diatomicsoft.core.database.entity.ModelPost

data class PostsState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val posts: List<ModelPost> = emptyList(),
    val filteredPosts: List<ModelPost> = emptyList(),
    val searchQuery: String = "",
    val error: String? = null
)

sealed class PostsIntent {
    object RefreshData : PostsIntent()
    data class UpdateSearchQuery(val query: String) : PostsIntent()
}

sealed class PostsScreenEffect {
    data class ShowSnackBar(val message: String) : PostsScreenEffect()
}