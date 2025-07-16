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
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.diatomicsoft.core.navigation.NavigationRoute
import com.diatomicsoft.core.navigation.PostsRoute
import com.diatomicsoft.core.navigation.TOP_LEVEL_ROUTES
import com.diatomicsoft.core.navigation.TopLevelBackStack
import com.diatomicsoft.feature.posts.navigation.postsScreenEntry
import com.diatomicsoft.feature.album.navigation.albumsScreenEntry
import com.diatomicsoft.feature.album.navigation.imagesScreenEntry
import com.diatomicsoft.feature.posts.presentation.post_details.PostDetailsScreenRoute
import com.diatomicsoft.feature.todo.navigation.todoScreenEntry
import com.diatomicsoft.feature.users.navigation.usersScreenEntry
import com.diatomicsoft.feature.users.navigation.userDetailsScreenEntry

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
                albumsScreenEntry { albumId ->
                    topLevelBackStack.add(NavigationRoute.ImagesRoute(albumId))
                }
                usersScreenEntry { userId ->
                    topLevelBackStack.add(NavigationRoute.UserDetailsRoute(userId))
                }
                todoScreenEntry()

                entry<NavigationRoute.PostDetailsRoute> { key ->
                    PostDetailsScreenRoute(key.postId, key.title, key.body)
                }

                userDetailsScreenEntry()

                imagesScreenEntry()

            })
    }
}






