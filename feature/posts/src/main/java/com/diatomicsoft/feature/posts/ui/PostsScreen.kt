package com.diatomicsoft.feature.posts.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.diatomicsoft.core.database.entity.ModelPost
import com.diatomicsoft.core.navigation.NavigationDestination
import com.diatomicsoft.core.ui.ErrorComponent
import com.diatomicsoft.core.ui.ShimmerLoading
import com.diatomicsoft.feature.posts.components.SearchComponent
import com.diatomicsoft.feature.posts.components.SearchResultsInfo


@Composable
fun PostsScreenRoute(
    viewModel: PostsViewModel = hiltViewModel(),
    onPostClick: (NavigationDestination) -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.fetchPosts()
    }

    val state = viewModel.postState

    PostsScreen(state, viewModel::onIntent, onPostClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsScreen(
    state: PostsState,
    intent: (PostsIntent) -> Unit,
    onPostClick: (NavigationDestination) -> Unit,
) {

    PullToRefreshBox(
        isRefreshing = state.isRefreshing,
        onRefresh = { intent(PostsIntent.RefreshData) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Search Component
            SearchComponent(
                query = state.searchQuery,
                onQueryChange = { newQuery ->
                    intent(PostsIntent.UpdateSearchQuery(newQuery))
                },
                onSearch = { /* Search is handled automatically through filteredPosts */ },
                placeholder = "Search posts..."
            )

            // Search Results Info
            if (state.searchQuery.isNotEmpty()) {
                SearchResultsInfo(
                    query = state.searchQuery,
                    resultCount = state.filteredPosts.size
                )
            }

            // Content
            when {
                state.error != null && state.posts.isEmpty() -> {
                    ErrorComponent(
                        errorMessage = state.error,
                        onRetry = { intent(PostsIntent.RefreshData) },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                state.isLoading && state.posts.isEmpty() -> {
                    ShimmerLoading()
                }

                else -> {
                    PostsList(
                        posts = if (state.searchQuery.isNotEmpty()) state.filteredPosts else state.posts,
                        onPostClick = onPostClick,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}


@Composable
private fun PostsList(
    posts: List<ModelPost>,
    onPostClick: (NavigationDestination) -> Unit,
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
    onPostClick: (NavigationDestination) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onPostClick(
                    NavigationDestination.ToPostsDetails(
                        post.id,
                        post.title,
                        post.body
                    )
                )
            },
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