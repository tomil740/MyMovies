package com.example.mymovies.presntation.MovieItemPage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieItemScreen(
    movieItemStateAndEvents: MovieItemStateAndEvents
) {
    val uiState = movieItemStateAndEvents.uiState
    val movie = uiState.movieModuleItem

    var isFavoriteButtonFocused by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background)
            .onPreviewKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown) {
                    when (event.key) {
                        Key.Enter -> {
                            if (isFavoriteButtonFocused) {
                                movieItemStateAndEvents.onFavorite()
                                true
                            } else false
                        }
                        else -> false
                    }
                } else false
            }
    ) {
        // Background Image
        Image(
            painter = rememberImagePainter(movie.backgroundImgUrl),
            contentDescription = "Movie background",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop
        )

        // Gradient overlay to allow background image visibility but emphasize text
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                            MaterialTheme.colorScheme.primary.copy(alpha = 0f)
                        )
                    )
                )
        )

        // Top Bar with back button and favorite button
        TopAppBar(
            title = {},
            navigationIcon = {
                IconButton(
                    onClick = { movieItemStateAndEvents.onNavBack() },
                    modifier = Modifier.size(32.dp) // Increased icon size
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = { movieItemStateAndEvents.onFavorite() },
                    modifier = Modifier
                        .size(32.dp)
                        .onFocusChanged { isFavoriteButtonFocused = it.isFocused }
                ) {
                    Icon(
                        Icons.Filled.Favorite,
                        tint = if (uiState.favoriteStatus) Color.Red else Color.White,
                        contentDescription = "Favorite"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                titleContentColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(8.dp)
        )

        // Movie Poster (inside Card)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 180.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(corner = CornerSize(16.dp)),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .width(220.dp)
                    .height(320.dp)
            ) {
                Image(
                    painter = rememberImagePainter(movie.mainImgUrl),
                    contentDescription = "Movie Poster",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        // Movie Title, Release Date, and Overview
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 320.dp)
                .align(Alignment.TopStart)
        ) {
            // Movie Title
            Text(
                text = movie.title,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Release Date
            Text(
                text = "Release Date: ${movie.releaseDate}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Vote Average Component
            VoteAverageIndicator(voteAverage = uiState.movieModuleItem.voteAverage)

            Spacer(modifier = Modifier.height(16.dp))

            // Movie Overview
            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 5,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
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




