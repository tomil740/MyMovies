// Generated by Dagger (https://dagger.dev).
package com.example.mymovies.di;

import android.content.Context;
import com.example.mymovies.data.local.MoviesDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class AppModule_ProvideBeerDatabaseFactory implements Factory<MoviesDatabase> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideBeerDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public MoviesDatabase get() {
    return provideBeerDatabase(contextProvider.get());
  }

  public static AppModule_ProvideBeerDatabaseFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideBeerDatabaseFactory(contextProvider);
  }

  public static MoviesDatabase provideBeerDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideBeerDatabase(context));
  }
}