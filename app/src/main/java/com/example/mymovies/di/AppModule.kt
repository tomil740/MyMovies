package com.example.mymovies.di

import android.content.Context
import androidx.room.Room
import com.example.mymovies.data.local.MoviesDatabase
import com.example.mymovies.data.local.factory.AuthDataStore
import com.example.mymovies.data.remote.RemoteDao
import com.example.mymovies.data.remote.factory.OkHttpClientModule
import com.example.mymovies.data.repository.MoviesRepositoryImpl
import com.example.mymovies.domain.repository.MoviesRepository
import com.example.mymovies.domain.useCases.GetItemFavoriteState
import com.example.mymovies.domain.useCases.OnLoginEvent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBeerDatabase(@ApplicationContext context: Context): MoviesDatabase {
        return Room.databaseBuilder(
            context,
            MoviesDatabase::class.java,
            "movie.db"
        ).build()
    }
    @Provides
    @Singleton
    fun provideAuthDataStore(@ApplicationContext context: Context): AuthDataStore {
        return AuthDataStore(context)
    }
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClientModule.provideOkHttpClient()
    }

    @Provides
    fun provideRemoteDao(okHttpClient: OkHttpClient): RemoteDao {
        return RemoteDao(okHttpClient)
    }

    @Provides
    @Singleton
    fun provideMoviesRepository(moviesDb: MoviesDatabase, remoteDao: RemoteDao,@ApplicationContext context: Context,authDataStore:AuthDataStore): MoviesRepository {
        return MoviesRepositoryImpl(moviesDb,remoteDao,context ,authDataStore)
    }

    @Provides
    fun provideGetItemFav(repo:MoviesRepository): GetItemFavoriteState {
        return GetItemFavoriteState(repo)
    }

    @Provides
    fun provideOnLoginEvent(repo:MoviesRepository): OnLoginEvent {
        return OnLoginEvent(repo)
    }

    // Provide Context globally if needed
    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context:Context): Context {
        return context.applicationContext
    }


}