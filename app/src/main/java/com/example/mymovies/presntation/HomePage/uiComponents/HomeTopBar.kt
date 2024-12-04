package com.example.mymovies.presntation.HomePage.uiComponents

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mymovies.domain.models.UserStateUi


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    user:UserStateUi,
    onLoginClick: (Boolean) -> Unit,
) {

    TopAppBar(
        title = {
            Text(
                text = if (user.id!=-1) "Hello, ${user.name}" else "Hey guest, please log in",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        actions = {
            when {
                user.isLoading -> {
                    Button(onClick = { onLoginClick(true) }) {

                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 16.dp)
                        )
                    }
                }
                (user.id!=-1)  -> {
                    Button(onClick = {onLoginClick(false)}) {
                        Text("Logout", style = MaterialTheme.typography.bodyLarge,)
                    }
                }
                else -> {
                    Button(onClick = {onLoginClick(false)}) {
                        Text("LogIn", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,  // Transparent to use gradient background
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                )
            )
    )
}
