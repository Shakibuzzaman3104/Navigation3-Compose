package com.diatomicsoft.feature.todo.navigation

import androidx.navigation3.runtime.EntryProviderBuilder
import androidx.navigation3.runtime.entry
import com.diatomicsoft.core.navigation.ToDoRoute
import com.diatomicsoft.feature.todo.ui.ToDoScreenRoute

fun EntryProviderBuilder<Any>.todoScreenEntry() {
    entry<ToDoRoute> { key ->
        ToDoScreenRoute()
    }
}