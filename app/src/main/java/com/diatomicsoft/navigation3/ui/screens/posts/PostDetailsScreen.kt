package com.diatomicsoft.navigation3.ui.screens.posts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

data class PostDetailsRoute(val id: Int)

@Composable
fun PostDetailsScreenRoute(
    postId: Int
) {
    PostDetailsScreen(postId)
}

@Composable
fun PostDetailsScreen(postId: Int) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
        content = {
            Text("Post Details: $postId")
        }
    )

}
