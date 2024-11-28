package com.example.mymovies.domain.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\u001d\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0006J\u0019\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u0005H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ%\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f2\u0006\u0010\u000f\u001a\u00020\u0005H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\nJ\'\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u00032\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0011H\u00a6@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0013\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0014"}, d2 = {"Lcom/example/mymovies/domain/repository/MoviesRepository;", "", "getAllFavoriteItemsId", "Lcom/example/mymovies/domain/util/Result;", "", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMovieById", "Lcom/example/mymovies/domain/models/MovieModule;", "id", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMoviePaging", "Lkotlinx/coroutines/flow/Flow;", "Landroidx/paging/PagingData;", "Lcom/example/mymovies/domain/models/MovieListItem;", "sortingId", "setFavorItemStatuesById", "", "statues", "(IZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public abstract interface MoviesRepository {
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getMoviePaging(int sortingId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<androidx.paging.PagingData<com.example.mymovies.domain.models.MovieListItem>>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getMovieById(int id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.mymovies.domain.models.MovieModule> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object setFavorItemStatuesById(int id, boolean statues, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.mymovies.domain.util.Result<java.lang.Boolean>> $completion);
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getAllFavoriteItemsId(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.mymovies.domain.util.Result<? extends java.util.List<java.lang.Integer>>> $completion);
}