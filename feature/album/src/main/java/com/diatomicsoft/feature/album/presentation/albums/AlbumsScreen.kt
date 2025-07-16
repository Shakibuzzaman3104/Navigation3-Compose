package com.diatomicsoft.feature.album.presentation.albums

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.diatomicsoft.core.database.entity.ModelAlbum
import com.diatomicsoft.core.ui.ErrorComponent
import com.diatomicsoft.core.ui.LoadingComponent
import com.diatomicsoft.core.ui.NetworkErrorComponent

@Composable
fun AlbumsScreenRoute(navigateToImages: (Int) -> Unit) {
    val viewModel: AlbumsViewModel = hiltViewModel()
    val currentState by viewModel.albumsState.collectAsState()
    
    LaunchedEffect(key1 = true) {
        viewModel.getAlbums()
    }
    
    AlbumsScreen(
        state = currentState,
        navigateToImages = navigateToImages,
        onRetry = { viewModel.getAlbums() }
    )
}

@Composable
fun AlbumsScreen(
    state: AlbumsState,
    navigateToImages: (Int) -> Unit,
    onRetry: () -> Unit
) {
    when (state) {
        is AlbumsState.Loading -> {
            LoadingComponent(
                modifier = Modifier.fillMaxSize(),
                message = "Loading albums..."
            )
        }

        is AlbumsState.Success -> {
            AlbumsList(albums = state.albums ?: emptyList(), navigateToImages = navigateToImages)
        }

        is AlbumsState.Error -> {
            val isNetworkError = state.message.contains("internet", ignoreCase = true) ||
                    state.message.contains("network", ignoreCase = true) ||
                    state.message.contains("connection", ignoreCase = true)
            
            if (isNetworkError) {
                NetworkErrorComponent(
                    onRetry = onRetry,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                ErrorComponent(
                    errorMessage = state.message,
                    onRetry = onRetry,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun AlbumsList(albums: List<ModelAlbum>, navigateToImages: (Int) -> Unit) {
    if (albums.isEmpty()) {
        EmptyAlbumsState(modifier = Modifier.fillMaxSize())
    } else {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(
                items = albums,
                key = { it.id }
            ) { album ->
                AlbumItem(album = album, navigateToImages = navigateToImages)
            }
        }
    }
}

@Composable
private fun EmptyAlbumsState(
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
                text = "No albums found",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Album list is empty",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun AlbumItem(album: ModelAlbum, navigateToImages: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { navigateToImages(album.id) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = album.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}