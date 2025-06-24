package com.diatomicsoft.navigation3.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.LocalPostOffice
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.ui.graphics.vector.ImageVector

sealed interface TopLevelRoute {
    val icon: ImageVector
}

data object Posts : TopLevelRoute {
    override val icon = Icons.Default.LocalPostOffice
}

data object Albums : TopLevelRoute{
    override val icon: ImageVector = Icons.Default.Album
}

data object Users: TopLevelRoute{
    override val icon: ImageVector = Icons.Filled.VerifiedUser
}

 val TOP_LEVEL_ROUTES: List<TopLevelRoute> = listOf(Posts, Albums, Users)
