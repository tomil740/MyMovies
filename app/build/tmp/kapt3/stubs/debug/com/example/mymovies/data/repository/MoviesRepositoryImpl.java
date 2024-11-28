package com.example.mymovies.data.repository;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u001d\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\bH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ\u0019\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\nH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ%\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00130\u00120\u00112\u0006\u0010\u0014\u001a\u00020\nH\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000fJ\'\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00160\b2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u0017\u001a\u00020\u0016H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0019"}, d2 = {"Lcom/example/mymovies/data/repository/MoviesRepositoryImpl;", "Lcom/example/mymovies/domain/repository/MoviesRepository;", "moviesDb", "Lcom/example/mymovies/data/local/MoviesDatabase;", "remoteDao", "Lcom/example/mymovies/data/remote/RemoteDao;", "(Lcom/example/mymovies/data/local/MoviesDatabase;Lcom/example/mymovies/data/remote/RemoteDao;)V", "getAllFavoriteItemsId", "Lcom/example/mymovies/domain/util/Result;", "", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMovieById", "Lcom/example/mymovies/domain/models/MovieModule;", "id", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMoviePaging", "Lkotlinx/coroutines/flow/Flow;", "Landroidx/paging/PagingData;", "Lcom/example/mymovies/domain/models/MovieListItem;", "sortingId", "setFavorItemStatuesById", "", "statues", "(IZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class MoviesRepositoryImpl implements com.example.mymovies.domain.repository.MoviesRepository {
    @org.jetbrains.annotations.NotNull
    private final com.example.mymovies.data.local.MoviesDatabase moviesDb = null;
    @org.jetbrains.annotations.NotNull
    private final com.example.mymovies.data.remote.RemoteDao remoteDao = null;
    
    public MoviesRepositoryImpl(@org.jetbrains.annotations.NotNull
    com.example.mymovies.data.local.MoviesDatabase moviesDb, @org.jetbrains.annotations.NotNull
    com.example.mymovies.data.remote.RemoteDao remoteDao) {
        super();
    }
    
    @java.lang.Override
    @kotlin.OptIn(markerClass = {androidx.paging.ExperimentalPagingApi.class})
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getMoviePaging(int sortingId, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlinx.coroutines.flow.Flow<androidx.paging.PagingData<com.example.mymovies.domain.models.MovieListItem>>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getMovieById(int id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.mymovies.domain.models.MovieModule> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object setFavorItemStatuesById(int id, boolean statues, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.mymovies.domain.util.Result<java.lang.Boolean>> $completion) {
        return null;
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object getAllFavoriteItemsId(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.mymovies.domain.util.Result<? extends java.util.List<java.lang.Integer>>> $completion) {
        return null;
    }
}