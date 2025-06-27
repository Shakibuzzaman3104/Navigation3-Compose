package com.diatomicsoft.navigation3.ui.screens.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PostsScreenRoute(
    viewModel: PostsViewModel = hiltViewModel(),
    onPostClick: (Int) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetPosts()
    }
    val state = viewModel.postState
    PostsScreen(state, onPostClick)
}

@Composable
fun PostsScreen(state: PostsState, onPostClick: (Int) -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        content = {
            LazyColumn {
                item {
                    Text("Posts")
                }

                items(state.posts.size) {
                    state.posts.forEach { post ->

                        Text(
                            post.title,
                            modifier = Modifier.clickable(true, onClick = { onPostClick(post.id) })
                        )

                    }
                }
            }
        }
    )

}

