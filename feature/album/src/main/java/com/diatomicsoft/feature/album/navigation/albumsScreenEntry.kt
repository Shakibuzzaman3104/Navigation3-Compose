package com.diatomicsoft.feature.album.navigation

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.entry
import com.diatomicsoft.core.navigation.AlbumsRoute
import com.diatomicsoft.core.navigation.NavigationRoute
import com.diatomicsoft.feature.album.presentation.albums.AlbumsScreenRoute
import com.diatomicsoft.feature.album.presentation.images.ImagesScreenRoute

fun EntryProviderBuilder<Any>.albumsScreenEntry(
    onAlbumClick: (Int) -> Unit
) {
    entry<AlbumsRoute> { key ->
        AlbumsScreenRoute(navigateToImages = onAlbumClick)
    }
}

fun EntryProviderBuilder<Any>.imagesScreenEntry() {
    entry<NavigationRoute.ImagesRoute> { key ->
        ImagesScreenRoute(key.albumId)
    }
}