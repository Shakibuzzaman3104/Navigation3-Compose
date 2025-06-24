package com.diatomicsoft.navigation3.ui.screens.posts

import com.diatomicsoft.navigation3.data.model.Post

data class PostsState(val posts: List<Post> = emptyList<Post>(), val isPostsLoading: Boolean = false)