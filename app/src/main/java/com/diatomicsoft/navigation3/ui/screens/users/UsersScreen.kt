package com.diatomicsoft.navigation3.ui.screens.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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

@Composable
fun UsersScreenRoute(navigateToDetails: (Int) -> Unit) {
    val viewModel: UsersViewModel = hiltViewModel()
    val currentState by viewModel.usersState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.getUsers()
    }
    UsersScreen(state = currentState, navigateToDetails = navigateToDetails)
}

@Composable
fun UsersScreen(state: UsersState, navigateToDetails: (Int) -> Unit) {
    when (state) {
        is UsersState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is UsersState.Success -> {
            UsersList(users = state.users, navigateToDetails = navigateToDetails)
        }

        is UsersState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.message)
            }
        }
    }

}

@Composable
fun UsersList(users: List<ModelUser>, navigateToDetails: (Int) -> Unit) {
    LazyColumn {
        items(users) { user ->
            UserItem(user = user, navigateToDetails = navigateToDetails)
            Spacer(modifier = Modifier.padding(horizontal = 16.dp))
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