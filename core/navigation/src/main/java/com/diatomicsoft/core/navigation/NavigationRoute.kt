package com.diatomicsoft.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Pages
import androidx.compose.material.icons.filled.People
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
    override val icon = Icons.Default.Pages
}

@Serializable
data object AlbumsRoute : TopLevelRoute {
    override val icon: ImageVector = Icons.Default.Album
}

@Serializable
data object UsersRoute : TopLevelRoute {
    override val icon: ImageVector = Icons.Filled.People
}

@Serializable
data object ToDoRoute : TopLevelRoute {
    override val icon: ImageVector = Icons.AutoMirrored.Filled.List
}

val TOP_LEVEL_ROUTES: List<TopLevelRoute> = listOf(PostsRoute, AlbumsRoute, ToDoRoute, UsersRoute)

