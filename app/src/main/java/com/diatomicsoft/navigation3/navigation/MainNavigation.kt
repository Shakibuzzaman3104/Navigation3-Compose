package com.diatomicsoft.navigation3.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.diatomicsoft.core.navigation.AlbumsRoute
import com.diatomicsoft.core.navigation.NavigationRoute
import com.diatomicsoft.core.navigation.PostsRoute
import com.diatomicsoft.core.navigation.TOP_LEVEL_ROUTES
import com.diatomicsoft.core.navigation.ToDoRoute
import com.diatomicsoft.core.navigation.TopLevelBackStack
import com.diatomicsoft.core.navigation.UsersRoute
import com.diatomicsoft.feature.posts.navigation.postsScreenEntry
import com.diatomicsoft.navigation3.ui.screens.albums.AlbumsScreenRoute
import com.diatomicsoft.navigation3.ui.screens.albums.ImagesScreenRoute
import com.diatomicsoft.navigation3.ui.screens.posts.PostDetailsRoute
import com.diatomicsoft.navigation3.ui.screens.posts.PostDetailsScreenRoute
import com.diatomicsoft.navigation3.ui.screens.todo.ToDoScreenRoute
import com.diatomicsoft.navigation3.ui.screens.users.UserDetailsScreenRoute
import com.diatomicsoft.navigation3.ui.screens.users.UsersScreenRoute

@Composable
fun MainNavigation() {

    val topLevelBackStack = remember { TopLevelBackStack<Any>(PostsRoute) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                TOP_LEVEL_ROUTES.forEach { topLevelRoute ->

                    val isSelected =
                        derivedStateOf { topLevelRoute == topLevelBackStack.topLevelKey }

                    NavigationBarItem(selected = isSelected.value, onClick = {
                        topLevelBackStack.addTopLevel(topLevelRoute)
                    }, icon = {
                        Icon(
                            imageVector = topLevelRoute.icon, contentDescription = null
                        )
                    })

                }
            }
        }) { padding ->
        NavDisplay(
            modifier = Modifier.padding(padding),
            backStack = topLevelBackStack.backStack,
            onBack = { topLevelBackStack.removeLast() },
            entryProvider = entryProvider {
                postsScreenEntry {
                    gotoDestination(it, topLevelBackStack)
                }
                entry<AlbumsRoute> {
                    AlbumsScreenRoute { albumId ->
                        topLevelBackStack.add(NavigationRoute.ImagesRoute(albumId))
                    }
                }
                entry<UsersRoute> {
                    UsersScreenRoute { userId ->
                        topLevelBackStack.add(NavigationRoute.UserDetailsRoute(userId))
                    }
                }
                entry<ToDoRoute> {
                    ToDoScreenRoute()
                }

                entry<PostDetailsRoute> { key ->
                    PostDetailsScreenRoute(key.postId, key.title, key.body)
                }

                userDetailsScreenEntry()

                imagesScreenEntry()

            })
    }
}


private fun EntryProviderBuilder<Any>.albumsScreenEntry(topLevelBackStack: TopLevelBackStack<Any>) {
    entry<AlbumsRoute> {
        AlbumsScreenRoute { albumId ->
            topLevelBackStack.add(NavigationRoute.ImagesRoute(albumId))
        }
    }
}

private fun EntryProviderBuilder<Any>.toDoScreenEntry() {
    entry<ToDoRoute> {
        ToDoScreenRoute()
    }
}

private fun EntryProviderBuilder<Any>.usersScreenEntry(topLevelBackStack: TopLevelBackStack<Any>) {
    entry<UsersRoute> {
        UsersScreenRoute { userId ->
            topLevelBackStack.add(NavigationRoute.UserDetailsRoute(userId))
        }
    }
}

private fun EntryProviderBuilder<Any>.userDetailsScreenEntry() {
    entry<NavigationRoute.UserDetailsRoute> { key ->
        UserDetailsScreenRoute(key.userId)
    }
}

private fun EntryProviderBuilder<Any>.imagesScreenEntry() {
    entry<NavigationRoute.ImagesRoute> { key ->
        ImagesScreenRoute(key.albumId)
    }
}

