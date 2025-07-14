package com.diatomicsoft.navigation3.ui.screens.users

import com.diatomicsoft.core.database.entity.ModelUser

sealed class UsersState {
    object Loading : UsersState()
    data class Success(val users: List<ModelUser>) : UsersState()
    data class Error(val message: String) : UsersState()
}