package com.example.mymovies.data.local;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\bg\u0018\u00002\u00020\u0001J\u0011\u0010\u0002\u001a\u00020\u0003H\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\tJ\u0014\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00060\u000bH\'J\u001f\u0010\f\u001a\u00020\u00032\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u000eH\u00a7@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000f\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0010"}, d2 = {"Lcom/example/mymovies/data/local/MovieDao;", "", "clearAll", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getMovieById", "Lcom/example/mymovies/data/local/MovieEntity;", "id", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "pagingSource", "Landroidx/paging/PagingSource;", "upsertAll", "movies", "", "(Ljava/util/List;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
@androidx.room.Dao
public abstract interface MovieDao {
    
    @androidx.room.Upsert
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object upsertAll(@org.jetbrains.annotations.NotNull
    java.util.List<com.example.mymovies.data.local.MovieEntity> movies, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM movieentity WHERE id = :id")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object getMovieById(int id, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.mymovies.data.local.MovieEntity> $completion);
    
    @androidx.room.Query(value = "SELECT * FROM movieentity")
    @org.jetbrains.annotations.NotNull
    public abstract androidx.paging.PagingSource<java.lang.Integer, com.example.mymovies.data.local.MovieEntity> pagingSource();
    
    @androidx.room.Query(value = "DELETE FROM movieentity")
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Object clearAll(@org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion);
}