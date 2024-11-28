// Generated by Dagger (https://dagger.dev).
package com.plcoding.composepaging3caching.presentation.movieItemPage;

import com.example.mymovies.domain.repository.MoviesRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class MovieItemViewModel_Factory implements Factory<MovieItemViewModel> {
  private final Provider<MoviesRepository> moviesRepoProvider;

  public MovieItemViewModel_Factory(Provider<MoviesRepository> moviesRepoProvider) {
    this.moviesRepoProvider = moviesRepoProvider;
  }

  @Override
  public MovieItemViewModel get() {
    return newInstance(moviesRepoProvider.get());
  }

  public static MovieItemViewModel_Factory create(Provider<MoviesRepository> moviesRepoProvider) {
    return new MovieItemViewModel_Factory(moviesRepoProvider);
  }

  public static MovieItemViewModel newInstance(MoviesRepository moviesRepo) {
    return new MovieItemViewModel(moviesRepo);
  }
}