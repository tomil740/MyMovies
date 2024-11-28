// Generated by Dagger (https://dagger.dev).
package com.example.mymovies.di;

import com.example.mymovies.data.local.MoviesDatabase;
import com.example.mymovies.data.remote.RemoteDao;
import com.example.mymovies.domain.repository.MoviesRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class AppModule_ProvideBeerRepositoryFactory implements Factory<MoviesRepository> {
  private final Provider<MoviesDatabase> beerDbProvider;

  private final Provider<RemoteDao> remoteDaoProvider;

  public AppModule_ProvideBeerRepositoryFactory(Provider<MoviesDatabase> beerDbProvider,
      Provider<RemoteDao> remoteDaoProvider) {
    this.beerDbProvider = beerDbProvider;
    this.remoteDaoProvider = remoteDaoProvider;
  }

  @Override
  public MoviesRepository get() {
    return provideBeerRepository(beerDbProvider.get(), remoteDaoProvider.get());
  }

  public static AppModule_ProvideBeerRepositoryFactory create(
      Provider<MoviesDatabase> beerDbProvider, Provider<RemoteDao> remoteDaoProvider) {
    return new AppModule_ProvideBeerRepositoryFactory(beerDbProvider, remoteDaoProvider);
  }

  public static MoviesRepository provideBeerRepository(MoviesDatabase beerDb, RemoteDao remoteDao) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideBeerRepository(beerDb, remoteDao));
  }
}