package com.example.mymovies.domain.useCases

import com.example.mymovies.domain.models.UserStateUi
import com.example.mymovies.domain.repository.MoviesRepository
import com.example.mymovies.domain.util.Result


/*

This use case solves the problem of getting the item’s favorite state.However,
 I could not find a matching query or even a reasonable query to implement because the current
 method is very inefficient.I only implemented it
 because I have a small amount of data. For reference, I used paging for the same data to sort reasons…
 */
class GetItemFavoriteState(private val repo: MoviesRepository) {

    suspend operator fun invoke(itemId: Int, account: UserStateUi): Boolean {
        var currentPage = 1
        var hasMorePages = true

        while (hasMorePages) {
            when (val result = repo.getAllFavoriteItemsId(
                accountId = account.id,
                accountKey = account.key,
                page = currentPage
            )) {
                is Result.Success -> {
                    val favoriteIds = result.data

                    if (favoriteIds.contains(itemId)) {
                        return true // Item found
                    }

                    // Stop if no more data
                    if (favoriteIds.isEmpty()) {
                        hasMorePages = false
                    } else {
                        currentPage++
                    }
                }
                is Result.Error -> {
                    return false // Handle error
                }
            }
        }

        return false
    }
}
