package com.diatomicsoft.navigation3.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.LocalPostOffice
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface TopLevelRoute {
    val icon: ImageVector
}

data object PostsRoute : TopLevelRoute {
    override val icon = Icons.Default.LocalPostOffice
}

data object AlbumsRoute : TopLevelRoute {
    override val icon: ImageVector = Icons.Default.Album
}

data object UsersRoute : TopLevelRoute {
    override val icon: ImageVector = Icons.Filled.VerifiedUser
}

data object ToDoRoute : TopLevelRoute {
    override val icon: ImageVector = Icons.Filled.Note
}

val TOP_LEVEL_ROUTES: List<TopLevelRoute> = listOf(PostsRoute, AlbumsRoute, ToDoRoute, UsersRoute)
