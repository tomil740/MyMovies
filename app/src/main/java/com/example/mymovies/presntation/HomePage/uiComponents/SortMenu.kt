package com.example.mymovies.presntation.HomePage.uiComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun SortMenu(
    selectedOption: Int,
    onSortSelected: (Int) -> Unit
) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val sortOptions = listOf("My Favorites", "Popular", "Currently Broadcast")
    var focusedIndex by remember { mutableIntStateOf(-1) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(scrollState)
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyDown) {
                    when (event.key) {
                        Key.DirectionRight -> {
                            if (focusedIndex < sortOptions.lastIndex) {
                                focusedIndex++
                                coroutineScope.launch {
                                    scrollState.animateScrollTo(scrollState.value + 100)
                                }
                            }
                            true
                        }
                        Key.DirectionLeft -> {
                            if (focusedIndex > 0) {
                                focusedIndex--
                                coroutineScope.launch {
                                    scrollState.animateScrollTo(scrollState.value - 100)
                                }
                            }
                            true
                        }
                        Key.Enter -> {
                            onSortSelected(focusedIndex)
                            true
                        }
                        else -> false
                    }
                } else false
            },
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        sortOptions.forEachIndexed { index, option ->
            ElevatedButton(
                onClick = { onSortSelected(index) },
                modifier = if (index == focusedIndex){
                    Modifier.padding(4.dp).background(Color.Red)}else{
                    Modifier.padding(4.dp)},
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (index == selectedOption) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = option,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (index == selectedOption) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}