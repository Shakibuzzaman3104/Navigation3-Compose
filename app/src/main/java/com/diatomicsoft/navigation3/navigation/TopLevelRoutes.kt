package com.diatomicsoft.navigation3.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.LocalPostOffice
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

sealed interface TopLevelRoute {
    val icon: ImageVector
}

@Serializable
data object PostsRoute : TopLevelRoute {
    override val icon = Icons.Default.LocalPostOffice
}

@Serializable
data object AlbumsRoute : TopLevelRoute {
    override val icon: ImageVector = Icons.Default.Album
}

@Serializable
data class ImagesRoute(val albumId: Int)

@Serializable
data object UsersRoute : TopLevelRoute {
    override val icon: ImageVector = Icons.Filled.VerifiedUser
}

@Serializable
data class UserDetailsRoute(val userId: Int)

@Serializable
data object ToDoRoute : TopLevelRoute {
    override val icon: ImageVector = Icons.Filled.Note
}

val TOP_LEVEL_ROUTES: List<TopLevelRoute> = listOf(PostsRoute, AlbumsRoute, ToDoRoute, UsersRoute)
