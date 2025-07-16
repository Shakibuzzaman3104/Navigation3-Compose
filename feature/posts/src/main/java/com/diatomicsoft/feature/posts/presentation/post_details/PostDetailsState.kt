package com.diatomicsoft.feature.posts.presentation.post_details

import com.diatomicsoft.core.database.entity.ModelComment

data class PostDetailsState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val comments: List<ModelComment> = emptyList<ModelComment>(),
    val postTitle: String? = null,
    val postDescription: String? = null,
    val error: String? = null
)

sealed class PostDetailsIntent {
    object RefreshData : PostDetailsIntent()
}

sealed class PostDetailsScreenEffect {
    data class ShowSnackBar(val message: String) : PostDetailsScreenEffect()
}