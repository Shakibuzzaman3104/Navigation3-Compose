package com.diatomicsoft.navigation3.ui.screens.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.diatomicsoft.navigation3.data.model.ModelPost

@Composable
fun PostsScreenRoute(
    viewModel: PostsViewModel = hiltViewModel(),
    onPostClick: (Int, String, String?) -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetPosts()
    }
    val state = viewModel.postState
    PostsScreen(state, onPostClick)
}

@Composable
fun PostsScreen(state: PostsState, onPostClick: (Int, String, String?) -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        if (state.error != null) {
            Text(text = state.error, color = MaterialTheme.colorScheme.error)
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.posts) { post ->
                    PostItem(post = post) { postId, title, body ->
                        onPostClick(postId, title, body)
                    }
                    Spacer(modifier = Modifier.padding(horizontal = 16.dp))
                }
            }
        }
        if (state.isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun PostItem(post: ModelPost, onPostClick: (Int, String, String?) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onPostClick(post.id, post.title, post.body) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

