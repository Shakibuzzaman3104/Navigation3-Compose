package com.diatomicsoft.core.navigation

import androidx.compose.ui.Modifier

data class ScreenParams(
    val modifier: Modifier = Modifier,
    val destination: (NavigationDestination) -> Unit = {},
    val onBackPress: () -> Unit = {}
)



