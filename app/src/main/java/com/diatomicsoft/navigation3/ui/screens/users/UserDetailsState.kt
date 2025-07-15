package com.diatomicsoft.navigation3.ui.screens.users

import com.diatomicsoft.core.database.entity.ModelUser

sealed class UserDetailsState {
    object Loading : UserDetailsState()
    data class Success(val user: ModelUser?) : UserDetailsState()
    data class Error(val message: String) : UserDetailsState()
}