package com.diatomicsoft.navigation3.ui.screens.posts

import com.diatomicsoft.navigation3.data.model.ModelPost

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