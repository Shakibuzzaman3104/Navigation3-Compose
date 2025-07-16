package com.diatomicsoft.core.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ErrorComponent(
    errorMessage: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    showRetryButton: Boolean = true
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.ErrorOutline,
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = errorMessage,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onErrorContainer,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        if (showRetryButton) {
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Try Again")
            }
        }
    }

}

@Composable
fun NetworkErrorComponent(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    ErrorComponent(
        errorMessage = "Please check your internet connection and try again.",
        onRetry = onRetry,
        modifier = modifier
    )
}

@Composable
fun GenericErrorComponent(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    ErrorComponent(
        errorMessage = "An unexpected error occurred. Please try again.",
        onRetry = onRetry,
        modifier = modifier
    )
}
