package com.diatomicsoft.navigation3.ui.screens.posts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.diatomicsoft.navigation3.data.model.ModelPost
import com.diatomicsoft.navigation3.ui.components.ErrorComponent
import com.diatomicsoft.navigation3.ui.components.SearchComponent
import com.diatomicsoft.navigation3.ui.components.SearchResultsInfo
import com.diatomicsoft.navigation3.ui.components.ShimmerLoading
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreenRoute(
    viewModel: PostsViewModelEnhanced = hiltViewModel(),
    onPostClick: (Int, String, String?) -> Unit
) {
    val state = viewModel.postState
    val searchQuery = viewModel.searchQuery
    val filteredPosts = viewModel.filteredPosts
    
    LaunchedEffect(Unit) {
        viewModel.fetchPosts()
    }
    
    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = { viewModel.refreshPosts() }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Search Component
            SearchComponent(
                query = searchQuery,
                onQueryChange = viewModel::updateSearchQuery,
                onSearch = { /* Search is handled automatically through filteredPosts */ },
                placeholder = "Search posts..."
            )
            
            // Search Results Info
            if (searchQuery.isNotEmpty()) {
                SearchResultsInfo(
                    query = searchQuery,
                    resultCount = filteredPosts.size
                )
            }
            
            // Content
            when {
                state.error != null && state.posts.isEmpty() -> {
                    ErrorComponent(
                        errorMessage = state.error,
                        onRetry = { viewModel.fetchPosts() },
                        modifier = Modifier.fillMaxSize()
                    )
                }
                state.isLoading && state.posts.isEmpty() -> {
                    ShimmerLoading()
                }
                else -> {
                    PostsList(
                        posts = if (searchQuery.isNotEmpty()) filteredPosts else state.posts,
                        onPostClick = onPostClick,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

// Keep the old function name for backward compatibility
@Composable
fun PostsScreenEnhanced(
    onPostClick: (Int, String, String?) -> Unit
) {
    PostsScreenRoute(onPostClick = onPostClick)
}

@Composable
private fun PostsList(
    posts: List<ModelPost>,
    onPostClick: (Int, String, String?) -> Unit,
    modifier: Modifier = Modifier
) {
    if (posts.isEmpty()) {
        EmptyPostsState(modifier = modifier)
    } else {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(
                items = posts,
                key = { it.id }
            ) { post ->
                EnhancedPostItem(
                    post = post,
                    onPostClick = onPostClick
                )
            }
        }
    }
}

@Composable
private fun EmptyPostsState(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No posts found",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Try adjusting your search criteria",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun EnhancedPostItem(
    post: ModelPost,
    onPostClick: (Int, String, String?) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onPostClick(post.id, post.title, post.body) },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Post ID Badge
            Surface(
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.width(IntrinsicSize.Min)
            ) {
                Text(
                    text = "#${post.id}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Title
            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Body
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight
            )
        }
    }
}

// Keep the original simple PostItem for backward compatibility
@Composable
fun PostItem(post: ModelPost, onPostClick: (Int, String, String?) -> Unit) {
    EnhancedPostItem(post = post, onPostClick = onPostClick)
}

// Original PostsScreen function for backward compatibility
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