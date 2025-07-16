package com.diatomicsoft.feature.users.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.diatomicsoft.core.database.entity.ModelUser
import com.diatomicsoft.core.ui.ErrorComponent
import com.diatomicsoft.core.ui.LoadingComponent
import com.diatomicsoft.core.ui.NetworkErrorComponent


@Composable
fun UserDetailsScreenRoute(userId: Int) {
    val viewModel: UserDetailsViewModel = hiltViewModel()
    val currentState by viewModel.userDetailsState.collectAsState()
    
    LaunchedEffect(key1 = true) {
        viewModel.getUserDetails(userId)
    }
    
    UserDetailsScreen(
        state = currentState,
        onRetry = { viewModel.getUserDetails(userId) }
    )
}

@Composable
fun UserDetailsScreen(
    state: UserDetailsState,
    onRetry: () -> Unit
) {
    when (state) {
        is UserDetailsState.Loading -> {
            LoadingComponent(
                modifier = Modifier.fillMaxSize(),
                message = "Loading user details..."
            )
        }

        is UserDetailsState.Success -> {
            state.user?.let { user ->
                UserDetails(user = user)
            } ?: run {
                ErrorComponent(
                    errorMessage = "User not found",
                    onRetry = onRetry,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        is UserDetailsState.Error -> {
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
fun UserDetails(user: ModelUser) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = user.phone,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = user.website,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}