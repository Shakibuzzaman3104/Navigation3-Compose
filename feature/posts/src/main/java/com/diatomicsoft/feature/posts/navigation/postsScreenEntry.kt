package com.diatomicsoft.feature.posts.navigation

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.entry
import com.diatomicsoft.core.navigation.NavigationDestination
import com.diatomicsoft.core.navigation.NavigationRoute
import com.diatomicsoft.core.navigation.PostsRoute
import com.diatomicsoft.feature.posts.ui.PostDetailsScreenRoute
import com.diatomicsoft.feature.posts.ui.PostsScreenRoute

fun EntryProviderBuilder<Any>.postsScreenEntry(
    onItemClick: (NavigationDestination) -> Unit
) {
    entry<PostsRoute> { key ->
        PostsScreenRoute(onPostClick = onItemClick)
    }
}

fun EntryProviderBuilder<Any>.postsDetailsScreenEntry() {
    entry<NavigationRoute.PostDetailsRoute> { key ->
        PostDetailsScreenRoute(key.postId, key.title, key.body)
    }
}