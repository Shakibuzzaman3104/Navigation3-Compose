package com.diatomicsoft.navigation3.ui.screens.posts

import com.diatomicsoft.navigation3.data.model.ModelPost

data class PostsState(val posts: List<ModelPost> = emptyList<ModelPost>(), val isPostsLoading: Boolean = false)