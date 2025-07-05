package com.diatomicsoft.navigation3.ui.screens.albums

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.diatomicsoft.navigation3.data.model.ModelAlbum

@Composable
fun AlbumsScreenRoute(navigateToImages: (Int) -> Unit) {
    val viewModel: AlbumsViewModel = hiltViewModel()
    val currentState by viewModel.albumsState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.getAlbums()
    }
    AlbumsScreen(state = currentState, navigateToImages = navigateToImages)
}

@Composable
fun AlbumsScreen(state: AlbumsState, navigateToImages: (Int) -> Unit) {
    when (state) {
        is AlbumsState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is AlbumsState.Success -> {
            AlbumsList(albums = state.albums ?: emptyList(), navigateToImages = navigateToImages)
        }

        is AlbumsState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.message)
            }
        }
    }
}

@Composable
fun AlbumsList(albums: List<ModelAlbum>, navigateToImages: (Int) -> Unit) {
    LazyColumn {
        items(albums) { album ->
            AlbumItem(album = album, navigateToImages = navigateToImages)
            Spacer(modifier = Modifier.padding(horizontal = 16.dp))
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