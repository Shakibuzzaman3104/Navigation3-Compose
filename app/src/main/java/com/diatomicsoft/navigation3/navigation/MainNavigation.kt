package com.diatomicsoft.navigation3.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.diatomicsoft.navigation3.ui.screens.posts.PostsRoute



@Composable
fun MainNavigation() {

    val topLevelBackStack = remember { TopLevelBackStack<Any>(Posts) }

    Scaffold(
        bottomBar = {

            NavigationBar {
                TOP_LEVEL_ROUTES.forEach { topLevelRoute ->

                    val isSelected =
                        derivedStateOf { topLevelRoute == topLevelBackStack.topLevelKey }

                    NavigationBarItem(
                        selected = isSelected.value,
                        onClick = {
                            topLevelBackStack.addTopLevel(topLevelBackStack)
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
            entryProvider = entryProvider {
                PostsRoute{

                }
            }
        )
    }

}

