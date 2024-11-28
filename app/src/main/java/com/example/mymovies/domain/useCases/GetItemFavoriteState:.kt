package com.example.mymovies.domain.useCases

import android.util.Log
import com.example.mymovies.domain.util.Result
import com.example.mymovies.domain.repository.MoviesRepository


/*

This use case solves the problem of getting the item’s favorite state.However,
 I could not find a matching query or even a reasonable query to implement because the current
 method is very inefficient.I only implemented it
 because I have a small amount of data. For reference, I used paging for the same data to sort reasons…
 */
class GetItemFavoriteState (private val repo: MoviesRepository) {
    operator suspend fun invoke(itemId: Int): Boolean {
        return  when(val a = repo.getAllFavoriteItemsId()){
            is Result.Success-> {
                a.data.contains(itemId)
            }
            is Result.Error->{
                false
            }
        }

    }
}