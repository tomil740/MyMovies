package com.example.mymovies.data.remote;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J+\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bJ+\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\tH\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u000bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\r"}, d2 = {"Lcom/example/mymovies/data/remote/RemoteDao;", "", "okHttpClient", "Lokhttp3/OkHttpClient;", "(Lokhttp3/OkHttpClient;)V", "getCurrentlyBroadcastMovies", "Lcom/example/mymovies/data/util/Result;", "Lcom/example/mymovies/data/remote/dtoModels/ResponseDto;", "page", "", "perPage", "(IILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getPopularMovies", "app_debug"})
public final class RemoteDao {
    @org.jetbrains.annotations.NotNull
    private final okhttp3.OkHttpClient okHttpClient = null;
    
    public RemoteDao(@org.jetbrains.annotations.NotNull
    okhttp3.OkHttpClient okHttpClient) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getPopularMovies(int page, int perPage, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.mymovies.data.util.Result<com.example.mymovies.data.remote.dtoModels.ResponseDto>> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Object getCurrentlyBroadcastMovies(int page, int perPage, @org.jetbrains.annotations.NotNull
    kotlin.coroutines.Continuation<? super com.example.mymovies.data.util.Result<com.example.mymovies.data.remote.dtoModels.ResponseDto>> $completion) {
        return null;
    }
}