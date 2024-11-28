import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mymovies.R
import com.example.mymovies.domain.models.MovieListItem

@Composable
fun MovieItem(movie: MovieListItem, onFavoriteClick: (Int) -> Unit, modifier: Modifier) {
    val imageUrl = "https://image.tmdb.org/t/p/w500${movie.posterImg}"

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
            .shadow(8.dp, shape = MaterialTheme.shapes.medium) // Add shadow for depth
            .clip(MaterialTheme.shapes.medium) // Rounded corners for the box
    ) {
        // Image height scales based on screen size
        val imageHeight = 300.dp.coerceAtMost(maxHeight / 2)

        // Movie Image (Poster) with rounded edges and elevated shadow
        AsyncImage(
            model = imageUrl,
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight) // Dynamically set image height
                .clip(RoundedCornerShape(16.dp)) // Rounded corners for the image
                .shadow(12.dp, shape = RoundedCornerShape(16.dp)) // Elevated shadow for the image
                .align(Alignment.TopCenter) // Center the image horizontally
                .offset(y = (-16).dp) // Raise the image slightly above the background
        )

        // Floating action button for Favorite (Top-left corner)
        FloatingActionButton(
            onClick = { onFavoriteClick(movie.id) },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp),
            shape = CircleShape,
            containerColor = MaterialTheme.colorScheme.secondary // Use theme secondary color
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_foreground), // Your favorite icon
                contentDescription = "Favorite",
                Modifier.size(86.dp)

            )
        }

        // Apply Gradient Background for Text Area (Image transparent at the top, solid at the bottom)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(imageHeight)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent, // More transparent at the top
                            MaterialTheme.colorScheme.surface.copy(alpha = 0.3f) // Subtle gradient towards the bottom
                        ),
                        startY = 0f,
                        endY = imageHeight.value // Gradient range adjusted dynamically
                    )
                )
                .align(Alignment.BottomStart)
        )

        // Movie Title and Subtitle (Release date, Vote average)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
                        )
                    ),
                    shape = MaterialTheme.shapes.medium
                )
                .shadow(4.dp, shape = MaterialTheme.shapes.medium) // Add shadow to the text box
                .clip(RoundedCornerShape(8.dp)) // Rounded corners for the text box
        ) {
            Text(
                text = movie.title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface // Use primary color for emphasis
                ),
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "Release: ${movie.releaseDate}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface // More subtle color for the release date
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // Circular Progress Bar (Vote Average) at the Bottom-Right Corner with Label
        CircularVoteProgressBar(voteAverage = movie.voteAverage)
    }
}

@Composable
fun CircularVoteProgressBar(voteAverage: Float) {
    val progress = (voteAverage * 10).coerceIn(0f, 100f) // Scale vote average to 0-100

    Box(
        modifier = Modifier
            .size(120.dp)  // Adjusted size for a better fit
            .padding(8.dp) // Reduced margin around the progress circle
            .offset(x = 250.dp, y = 150.dp) // Position the progress bar at the bottom-right
    ) {
        // Background Circle with margin
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f), shape = CircleShape)
        )

        // Foreground Circular Progress Indicator with less margin from the edge
        CircularProgressIndicator(
            progress = progress / 100f,
            modifier = Modifier
                .fillMaxSize() // Fill the Box size
                .padding(8.dp) // Reduced padding to fit the circle more snugly
                .align(Alignment.Center), // Align the circular progress to the center
            color = MaterialTheme.colorScheme.primary, // Use the primary color from the theme
            strokeWidth = 8.dp  // Adjusted stroke width for better visibility
        )

        // Percentage Text in the center of the circle (Increased text size)
        Text(
            text = "${progress.toInt()}%", // Show the percentage
            color = Color.White,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold), // Larger text size for emphasis
            modifier = Modifier.align(Alignment.Center) // Align the percentage text to the center
        )

        // Rating Label: Position it below the progress bar
        Text(
            text = "Rating",
            color = Color.White,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier
                .align(Alignment.BottomCenter) // Align the "Rating" label below the progress circle
                .padding(bottom = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MovieItemPreview() {
    MovieItem(
        movie = MovieListItem(
            id = 1,
            title = "Terrifier 3",
            overview = "A chilling sequel to the Art the Clown series.",
            voteAverage = 6.8f, // Example vote average (out of 10)
            releaseDate = "2024-10-09",
            posterImg = "/18TSJF1WLA4CkymvVUcKDBwUJ9F.jpg",
            page = 1
        ),
        onFavoriteClick = { movieId -> println("Movie $movieId marked as favorite") },
        modifier = Modifier.fillMaxSize()
    )
}


