package com.diatomicsoft.feature.users.navigation

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.entry
import com.diatomicsoft.core.navigation.NavigationRoute
import com.diatomicsoft.core.navigation.UsersRoute
import com.diatomicsoft.feature.users.ui.UserDetailsScreenRoute
import com.diatomicsoft.feature.users.ui.UsersScreenRoute

fun EntryProviderBuilder<Any>.usersScreenEntry(
    onUserClick: (Int) -> Unit
) {
    entry<UsersRoute> { key ->
        UsersScreenRoute(navigateToDetails = onUserClick)
    }
}

fun EntryProviderBuilder<Any>.userDetailsScreenEntry() {
    entry<NavigationRoute.UserDetailsRoute> { key ->
        UserDetailsScreenRoute(key.userId)
    }
}