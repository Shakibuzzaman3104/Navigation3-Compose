package com.diatomicsoft.navigation3.navigation

import android.util.Log
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.diatomicsoft.navigation3.ui.screens.albums.AlbumsScreenRoute
import com.diatomicsoft.navigation3.ui.screens.posts.PostDetailsRoute
import com.diatomicsoft.navigation3.ui.screens.posts.PostDetailsScreenRoute
import com.diatomicsoft.navigation3.ui.screens.posts.PostsScreenRoute
import com.diatomicsoft.navigation3.ui.screens.todo.ToDoScreenRoute
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

                    NavigationBarItem(
                        selected = isSelected.value,
                        onClick = {
                            topLevelBackStack.addTopLevel(topLevelRoute)
                        },
                        icon = {
                            Icon(
                                imageVector = topLevelRoute.icon,
                                contentDescription = null
                            )
                        }
                    )
                }
            }
        }
    ) { padding ->
        NavDisplay(
            backStack = topLevelBackStack.backStack,
            onBack = { topLevelBackStack.removeLast() },
            entryProvider = entryProvider<Any> {
                entry<PostsRoute> {
                    PostsScreenRoute { postId -> topLevelBackStack.add(PostDetailsRoute(postId)) }
                }
                entry<AlbumsRoute> {
                    AlbumsScreenRoute()
                }
                entry<UsersRoute> {
                    UsersScreenRoute()
                }
                entry<ToDoRoute> {
                    ToDoScreenRoute()
                }

                entry<PostDetailsRoute> { key->
                    PostDetailsScreenRoute(key.id)
                }
            }
        )
    }
}



