package com.diatomicsoft.feature.users.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.diatomicsoft.core.database.entity.ModelUser
import com.diatomicsoft.core.ui.ErrorComponent
import com.diatomicsoft.core.ui.LoadingComponent
import com.diatomicsoft.core.ui.NetworkErrorComponent

@Composable
fun UsersScreenRoute(navigateToDetails: (Int) -> Unit) {
    val viewModel: UsersViewModel = hiltViewModel()
    val currentState by viewModel.usersState.collectAsState()
    
    LaunchedEffect(key1 = true) {
        viewModel.getUsers()
    }
    
    UsersScreen(
        state = currentState,
        navigateToDetails = navigateToDetails,
        onRetry = { viewModel.getUsers() }
    )
}

@Composable
fun UsersScreen(
    state: UsersState,
    navigateToDetails: (Int) -> Unit,
    onRetry: () -> Unit
) {
    when (state) {
        is UsersState.Loading -> {
            LoadingComponent(
                modifier = Modifier.fillMaxSize(),
                message = "Loading users..."
            )
        }

        is UsersState.Success -> {
            UsersList(users = state.users, navigateToDetails = navigateToDetails)
        }

        is UsersState.Error -> {
            val isNetworkError = state.message.contains("internet", ignoreCase = true) ||
                    state.message.contains("network", ignoreCase = true) ||
                    state.message.contains("connection", ignoreCase = true)
            
            if (isNetworkError) {
                NetworkErrorComponent(
                    onRetry = onRetry,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                ErrorComponent(
                    errorMessage = state.message,
                    onRetry = onRetry,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@Composable
fun UsersList(users: List<ModelUser>, navigateToDetails: (Int) -> Unit) {
    if (users.isEmpty()) {
        EmptyUsersState(modifier = Modifier.fillMaxSize())
    } else {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(
                items = users,
                key = { it.id }
            ) { user ->
                UserItem(user = user, navigateToDetails = navigateToDetails)
            }
        }
    }
}

@Composable
private fun EmptyUsersState(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No users found",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "User list is empty",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun UserItem(user: ModelUser, navigateToDetails: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { navigateToDetails(user.id) }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}