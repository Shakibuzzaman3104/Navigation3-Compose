package com.diatomicsoft.navigation3.ui.screens.posts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

data class PostDetailsRoute(val postId: Int, val title: String, val body: String? = null)

@Composable
fun PostDetailsScreenRoute(
    viewModel: PostDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchComments()
    }

    PostDetailsScreen(state = viewModel.state, onIntent = viewModel::onIntent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailsScreen(onIntent: (PostDetailsIntent) -> Unit, state: PostDetailsState) {

    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = { onIntent(PostDetailsIntent.RefreshData) },
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {

            if (state.error != null) {
                Text(text = state.error, color = MaterialTheme.colorScheme.error)
            } else {
                LazyColumn {
                    item {
                        Text(text = state.postTitle ?: "No Title")
                        Text(text = state.postDescription ?: "No Description")
                    }
                    items(state.comments.size) { index ->
                        val comment = state.comments[index]
                        Text(text = comment.body, modifier = Modifier.padding(8.dp))
                    }
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            }

        }
    }
}
