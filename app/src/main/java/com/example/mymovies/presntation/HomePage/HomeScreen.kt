package com.example.mymovies.presntation.HomePage

import MovieItem
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.mymovies.domain.models.MovieListItem



@Composable
fun HomeScreen(
    onItemNav: (String) -> Unit,
    homeStatesAndEvents: HomeScreenStateAndEvents
) {
    val context = LocalContext.current
    val moviesData = homeStatesAndEvents.uiState.dataList?.collectAsLazyPagingItems()

    // Handle error states
    LaunchedEffect(key1 = moviesData?.loadState) {
        if (moviesData?.loadState?.refresh is LoadState.Error) {
            Toast.makeText(
                context,
                "Error: ${(moviesData.loadState.refresh as LoadState.Error).error.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Column for the top bar, sorting menu, and LazyColumn for the movie list
        Column(modifier = Modifier.fillMaxSize()) {
            // TopAppBar (fixed at the top)
            HomeTopBar(
                userName = homeStatesAndEvents.uiState.userName,
                isLoggedIn = homeStatesAndEvents.uiState.userName.isNotEmpty(),
                onLoginClick = { /* Handle login action */ }
            )

            // Sorting Menu (fixed at the top)
            SortMenu(
                selectedOption = homeStatesAndEvents.uiState.sortingOption,
                onSortSelected = { homeStatesAndEvents.onSorting(it) }
            )

            // Movie List (scrollable independently below the top bar)
            MovieList(beersData = moviesData, onItemNav = onItemNav)
        }
    }
}

@Composable
fun MovieList(
    beersData: LazyPagingItems<MovieListItem>?,
    onItemNav: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (beersData != null) {

            items(beersData) { beer ->
                if (beer != null) {
                    MovieItem(
                        movie = beer,
                        onFavoriteClick = { onItemNav(beer.id.toString()) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onItemNav(beer.id.toString()) }
                    )
                }

            }

        }

        // Show loading indicator for appending data
        item {
            if (beersData?.loadState?.append is LoadState.Loading) {
                CircularProgressIndicator(modifier = Modifier)//align(Alignment.CenterHorizontally))
            }
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(
    userName: String,
    isLoggedIn: Boolean,
    onLoginClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = if (isLoggedIn) "Hello, $userName" else "Hey guest, please log in",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        actions = {
            if (!isLoggedIn) {
                TextButton(onClick = onLoginClick) {
                    Text("Login", style = MaterialTheme.typography.bodyLarge)
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
                    colors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.secondary)
                )
            )
    )
}

@Composable
fun SortMenu(
    selectedOption: Int,
    onSortSelected: (Int) -> Unit
) {
    val sortOptions = listOf("Popularity", "Rating", "Release Date")
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        sortOptions.forEachIndexed { index, option ->
            ElevatedButton(
                onClick = { onSortSelected(index) },
                modifier = Modifier.padding(4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (index == selectedOption) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(text = option, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}
