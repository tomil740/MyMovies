package com.example.mymovies.data.remote;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B@\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012.\u0010\u0006\u001a*\b\u0001\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0007\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\fJ-\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0013H\u0096@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0014R;\u0010\u0006\u001a*\b\u0001\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\b\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\u0007X\u0082\u0004\u00f8\u0001\u0000\u00a2\u0006\u0004\n\u0002\u0010\rR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0015"}, d2 = {"Lcom/example/mymovies/data/remote/MoviesRemoteMediator;", "Landroidx/paging/RemoteMediator;", "", "Lcom/example/mymovies/data/local/MovieEntity;", "movieDb", "Lcom/example/mymovies/data/local/MoviesDatabase;", "apiFun", "Lkotlin/Function3;", "Lkotlin/coroutines/Continuation;", "Lcom/example/mymovies/domain/util/Result;", "Lcom/example/mymovies/data/remote/dtoModels/ResponseDto;", "", "(Lcom/example/mymovies/data/local/MoviesDatabase;Lkotlin/jvm/functions/Function3;)V", "Lkotlin/jvm/functions/Function3;", "load", "Landroidx/paging/RemoteMediator$MediatorResult;", "loadType", "Landroidx/paging/LoadType;", "state", "Landroidx/paging/PagingState;", "(Landroidx/paging/LoadType;Landroidx/paging/PagingState;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@kotlin.OptIn(markerClass = {androidx.paging.ExperimentalPagingApi.class})
public final class MoviesRemoteMediator extends androidx.paging.RemoteMediator<java.lang.Integer, com.example.mymovies.data.local.MovieEntity> {
    @org.jetbrains.annotations.NotNull
    private final com.example.mymovies.data.local.MoviesDatabase movieDb = null;
    @org.jetbrains.annotations.NotNull
    private final kotlin.jvm.functions.Function3<java.lang.Integer, java.lang.Integer, kotlin.coroutines.Continuation<? super com.example.mymovies.domain.util.Result<com.example.mymovies.data.remote.dtoModels.ResponseDto>>, java.lang.Object> apiFun = null;
    
    public MoviesRemoteMediator(@org.jetbrains.annotations.NotNull
    com.example.mymovies.data.local.MoviesDatabase movieDb, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function3<? super java.lang.Integer, ? super java.lang.Integer, ? super kotlin.coroutines.Continuation<? super com.example.mymovies.domain.util.Result<com.example.mymovies.data.remote.dtoModels.ResponseDto>>, ? extends java.lang.Object> apiFun) {
        super();
    }
    
    @java.lang.Override
    @org.jetbrains.annotations.Nullable
    public java.lang.Object load(@org.jetbrains.annotations.NotNull
    androidx.paging.LoadType loadType, @org.jetbrains.annotations.NotNull
    androidx.paging.PagingState<java.lang.Integer, com.example.mymovies.data.local.MovieEntity> state, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super androidx.paging.RemoteMediator.MediatorResult> $completion) {
        return null;
    }
}