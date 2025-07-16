package com.diatomicsoft.navigation3.navigation

import androidx.navigation3.runtime.EntryProviderBuilder
import com.diatomicsoft.core.navigation.NavigationDestination
import com.diatomicsoft.core.navigation.NavigationRoute
import com.diatomicsoft.core.navigation.TopLevelBackStack

fun EntryProviderBuilder<Any>.gotoDestination(
    destination: NavigationDestination,
    topLevelBackStack: TopLevelBackStack<Any>
) {
    when (destination) {
        is NavigationDestination.ToSplashScreen -> {

        }

        NavigationDestination.ToAlbums -> {}
        is NavigationDestination.ToImages -> {}
        NavigationDestination.ToPosts -> {}
        NavigationDestination.ToToDo -> {}
        is NavigationDestination.ToUserDetails -> {}
        NavigationDestination.ToUsers -> {}
        is NavigationDestination.ToPostsDetails -> {
            topLevelBackStack.add(
                NavigationRoute.PostDetailsRoute(
                    postId = destination.id,
                    title = destination.title,
                    body = destination.description
                )
            )
        }
    }

}