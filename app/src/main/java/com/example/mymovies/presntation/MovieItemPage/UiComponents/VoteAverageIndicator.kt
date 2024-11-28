package com.example.mymovies.presntation.MovieItemPage.UiComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VoteAverageIndicator(voteAverage: Float) {
    // Create a Circular Progress Bar to represent the vote average
    val percentage = (voteAverage / 10) * 100 // Assuming vote average is out of 10

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        CircularProgressIndicator(
            progress = percentage / 100f, // Normalize to 0..1
            strokeWidth = 8.dp,
            modifier = Modifier
                .size(120.dp) // Larger size for emphasis
                .padding(8.dp),
            color = MaterialTheme.colorScheme.secondary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Vote Average: ${"%.1f".format(voteAverage)}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}