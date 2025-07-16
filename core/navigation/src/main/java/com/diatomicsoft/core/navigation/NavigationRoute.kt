package com.diatomicsoft.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Note
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.LocalPostOffice
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import kotlinx.serialization.Serializable
import kotlin.let

@Serializable
sealed class NavigationRoute() {

    @Serializable
    data object SplashRoute : NavigationRoute()


    @Serializable
    data class PostDetailsRoute(
        val postId: Int = 0,
        val title: String? = null,
        val body: String? = null,
    ) : NavigationRoute()


    @Serializable
    data class ImagesRoute(
        val albumId: Int
    ) : NavigationRoute()

    @Serializable
    data class UserDetailsRoute(
        val userId: Int
    ) : NavigationRoute()


}

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
data object UsersRoute : TopLevelRoute {
    override val icon: ImageVector = Icons.Filled.VerifiedUser
}

@Serializable
data object ToDoRoute : TopLevelRoute {
    override val icon: ImageVector = Icons.Filled.Note
}

val TOP_LEVEL_ROUTES: List<TopLevelRoute> = listOf(PostsRoute, AlbumsRoute, ToDoRoute, UsersRoute)

