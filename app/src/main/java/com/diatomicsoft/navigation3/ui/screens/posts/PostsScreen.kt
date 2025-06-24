package com.diatomicsoft.navigation3.ui.screens.posts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.entry
import com.diatomicsoft.navigation3.navigation.Posts

@Composable
fun EntryProviderBuilder<Any>.PostsRoute(
    viewModel: PostsViewModel = hiltViewModel(),
    onItemClick: (id: Int) -> Unit
) {

    val state = viewModel.postState

    entry<Posts> {
        PostsScreen(
            state,
            onItemClick = onItemClick
        )
    }
}

@Composable
fun PostsScreen(state: PostsState, onItemClick: (Int) -> Unit) {

    Box(
        contentAlignment = Alignment.Center,
        content = {

            LazyColumn {

                item {
                    Text("Posts")
                }

                items(state.posts.size) {
                    state.posts.forEach { post ->

                        Text(post.title)

                    }
                }


            }

        }
    )

}

