package com.diatomicsoft.navigation3.ui.screens.albums

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.diatomicsoft.navigation3.data.model.ModelPhoto
import coil.compose.AsyncImage
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ImagesScreenRoute(albumId: Int) {
    val viewModel: ImagesViewModel = hiltViewModel()
    val currentState by viewModel.imagesState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.getImages(albumId)
    }
    ImagesScreen(state = currentState)
}

@Composable
fun ImagesScreen(state: ImagesState) {
    when (state) {
        is ImagesState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is ImagesState.Success -> {
            ImagesList(images = state.images ?: emptyList())
        }

        is ImagesState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.message)
            }
        }
    }
}

@Composable
fun ImagesList(images: List<ModelPhoto>) {
    LazyColumn {
        items(images) {
            ImageItem(image = it)
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