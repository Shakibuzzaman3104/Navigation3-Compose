package com.diatomicsoft.feature.album.presentation.images

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.diatomicsoft.core.database.entity.ModelPhoto
import com.diatomicsoft.core.ui.ErrorComponent
import com.diatomicsoft.core.ui.ImagesShimmerLoading
import com.diatomicsoft.core.ui.NetworkErrorComponent

@Composable
fun ImagesScreenRoute(albumId: Int) {
    val viewModel: ImagesViewModel = hiltViewModel()
    val currentState by viewModel.imagesState.collectAsState()
    
    LaunchedEffect(key1 = true) {
        viewModel.getImages(albumId)
    }
    
    ImagesScreen(
        state = currentState,
        onRetry = { viewModel.getImages(albumId) }
    )
}

@Composable
fun ImagesScreen(
    state: ImagesState,
    onRetry: () -> Unit
) {
    when (state) {
        is ImagesState.Loading -> {
            ImagesShimmerLoading(
                modifier = Modifier.fillMaxSize()
            )
        }

        is ImagesState.Success -> {
            ImagesList(images = state.images ?: emptyList())
        }

        is ImagesState.Error -> {
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
fun ImagesList(images: List<ModelPhoto>) {
    if (images.isEmpty()) {
        EmptyImagesState(modifier = Modifier.fillMaxSize())
    } else {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(
                items = images,
                key = { it.id }
            ) { image ->
                ImageItem(image = image)
            }
        }
    }
}

@Composable
private fun EmptyImagesState(
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
                text = "No images found",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "This album is empty",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ImageItem(image: ModelPhoto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            AsyncImage(
                model = image.url,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = image.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}