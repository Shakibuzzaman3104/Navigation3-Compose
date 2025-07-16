package com.diatomicsoft.core.navigation


sealed interface NavigationDestination {

    data class ToSplashScreen(
        val taskId: Int? = null,
    ) : NavigationDestination

    data object ToAlbums : NavigationDestination

    data object ToPosts : NavigationDestination

    data class ToPostsDetails(val id: Int, val title: String, val description: String?) :
        NavigationDestination

    data object ToUsers : NavigationDestination

    data class ToImages(
        val albumId: Int
    ) : NavigationDestination

    data class ToUserDetails(
        val userId: Int
    ) : NavigationDestination

    data object ToToDo : NavigationDestination

}