package com.diatomicsoft.feature.todo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.diatomicsoft.core.database.entity.ModelToDo
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import com.diatomicsoft.core.ui.ErrorComponent
import com.diatomicsoft.core.ui.LoadingComponent
import com.diatomicsoft.core.ui.NetworkErrorComponent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height

@Composable
fun ToDoScreenRoute() {
    val viewModel: ToDoViewModel = hiltViewModel()
    val currentState by viewModel.toDoState.collectAsState()
    
    LaunchedEffect(key1 = true) {
        viewModel.getToDos()
    }
    
    ToDoScreen(
        state = currentState,
        onRetry = { viewModel.getToDos() }
    )
}

@Composable
fun ToDoScreen(
    state: ToDoState,
    onRetry: () -> Unit
) {
    when (state) {
        is ToDoState.Loading -> {
            LoadingComponent(
                modifier = Modifier.fillMaxSize(),
                message = "Loading todos..."
            )
        }

        is ToDoState.Success -> {
            ToDoList(todos = state.todos ?: emptyList())
        }

        is ToDoState.Error -> {
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
fun ToDoList(todos: List<ModelToDo>) {
    if (todos.isEmpty()) {
        EmptyTodosState(modifier = Modifier.fillMaxSize())
    } else {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(
                items = todos,
                key = { it.id }
            ) { todo ->
                ToDoItem(todo = todo)
            }
        }
    }
}

@Composable
private fun EmptyTodosState(
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
                text = "No todos found",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Your todo list is empty",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ToDoItem(todo: ModelToDo) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = todo.completed, onCheckedChange = { /* Do nothing for now */ })
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    textDecoration = if (todo.completed) TextDecoration.LineThrough else null
                )
                Text(
                    text = "Completed: ${todo.completed}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}