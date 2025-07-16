package com.diatomicsoft.feature.posts.ui

import com.diatomicsoft.core.database.entity.ModelPost

data class PostsState(
    val isLoading: Boolean = false,
    val posts: List<ModelPost> = emptyList<ModelPost>(),
    val error: String? = null
)

sealed class PostsIntent {
    object RefreshData : PostsIntent()
}

sealed class PostsScreenEffect {
    data class ShowSnackBar(val message: String) : PostsScreenEffect()
}