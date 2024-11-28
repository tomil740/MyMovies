package com.example.mymovies.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.PagingSource;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.EntityUpsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.paging.LimitOffsetPagingSource;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MovieDao_Impl implements MovieDao {
  private final RoomDatabase __db;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  private final EntityUpsertionAdapter<MovieEntity> __upsertionAdapterOfMovieEntity;

  public MovieDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM movieentity";
        return _query;
      }
    };
    this.__upsertionAdapterOfMovieEntity = new EntityUpsertionAdapter<MovieEntity>(new EntityInsertionAdapter<MovieEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT INTO `MovieEntity` (`page`,`adult`,`backdropPath`,`genreIds`,`id`,`originalLanguage`,`originalTitle`,`overview`,`popularity`,`posterPath`,`releaseDate`,`title`,`video`,`voteAverage`,`voteCount`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @Nullable final MovieEntity entity) {
        statement.bindLong(1, entity.getPage());
        final int _tmp = entity.getAdult() ? 1 : 0;
        statement.bindLong(2, _tmp);
        if (entity.getBackdropPath() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getBackdropPath());
        }
        if (entity.getGenreIds() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getGenreIds());
        }
        statement.bindLong(5, entity.getId());
        if (entity.getOriginalLanguage() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getOriginalLanguage());
        }
        if (entity.getOriginalTitle() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getOriginalTitle());
        }
        if (entity.getOverview() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getOverview());
        }
        statement.bindDouble(9, entity.getPopularity());
        if (entity.getPosterPath() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getPosterPath());
        }
        if (entity.getReleaseDate() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getReleaseDate());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getTitle());
        }
        final int _tmp_1 = entity.getVideo() ? 1 : 0;
        statement.bindLong(13, _tmp_1);
        statement.bindDouble(14, entity.getVoteAverage());
        statement.bindLong(15, entity.getVoteCount());
      }
    }, new EntityDeletionOrUpdateAdapter<MovieEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE `MovieEntity` SET `page` = ?,`adult` = ?,`backdropPath` = ?,`genreIds` = ?,`id` = ?,`originalLanguage` = ?,`originalTitle` = ?,`overview` = ?,`popularity` = ?,`posterPath` = ?,`releaseDate` = ?,`title` = ?,`video` = ?,`voteAverage` = ?,`voteCount` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @Nullable final MovieEntity entity) {
        statement.bindLong(1, entity.getPage());
        final int _tmp = entity.getAdult() ? 1 : 0;
        statement.bindLong(2, _tmp);
        if (entity.getBackdropPath() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getBackdropPath());
        }
        if (entity.getGenreIds() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getGenreIds());
        }
        statement.bindLong(5, entity.getId());
        if (entity.getOriginalLanguage() == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.getOriginalLanguage());
        }
        if (entity.getOriginalTitle() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getOriginalTitle());
        }
        if (entity.getOverview() == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, entity.getOverview());
        }
        statement.bindDouble(9, entity.getPopularity());
        if (entity.getPosterPath() == null) {
          statement.bindNull(10);
        } else {
          statement.bindString(10, entity.getPosterPath());
        }
        if (entity.getReleaseDate() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getReleaseDate());
        }
        if (entity.getTitle() == null) {
          statement.bindNull(12);
        } else {
          statement.bindString(12, entity.getTitle());
        }
        final int _tmp_1 = entity.getVideo() ? 1 : 0;
        statement.bindLong(13, _tmp_1);
        statement.bindDouble(14, entity.getVoteAverage());
        statement.bindLong(15, entity.getVoteCount());
        statement.bindLong(16, entity.getId());
      }
    });
  }

  @Override
  public Object clearAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object upsertAll(final List<MovieEntity> movies,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __upsertionAdapterOfMovieEntity.upsert(movies);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object getMovieById(final int id, final Continuation<? super MovieEntity> $completion) {
    final String _sql = "SELECT * FROM movieentity WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<MovieEntity>() {
      @Override
      @NonNull
      public MovieEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfPage = CursorUtil.getColumnIndexOrThrow(_cursor, "page");
          final int _cursorIndexOfAdult = CursorUtil.getColumnIndexOrThrow(_cursor, "adult");
          final int _cursorIndexOfBackdropPath = CursorUtil.getColumnIndexOrThrow(_cursor, "backdropPath");
          final int _cursorIndexOfGenreIds = CursorUtil.getColumnIndexOrThrow(_cursor, "genreIds");
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfOriginalLanguage = CursorUtil.getColumnIndexOrThrow(_cursor, "originalLanguage");
          final int _cursorIndexOfOriginalTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "originalTitle");
          final int _cursorIndexOfOverview = CursorUtil.getColumnIndexOrThrow(_cursor, "overview");
          final int _cursorIndexOfPopularity = CursorUtil.getColumnIndexOrThrow(_cursor, "popularity");
          final int _cursorIndexOfPosterPath = CursorUtil.getColumnIndexOrThrow(_cursor, "posterPath");
          final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(_cursor, "releaseDate");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfVideo = CursorUtil.getColumnIndexOrThrow(_cursor, "video");
          final int _cursorIndexOfVoteAverage = CursorUtil.getColumnIndexOrThrow(_cursor, "voteAverage");
          final int _cursorIndexOfVoteCount = CursorUtil.getColumnIndexOrThrow(_cursor, "voteCount");
          final MovieEntity _result;
          if (_cursor.moveToFirst()) {
            final int _tmpPage;
            _tmpPage = _cursor.getInt(_cursorIndexOfPage);
            final boolean _tmpAdult;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfAdult);
            _tmpAdult = _tmp != 0;
            final String _tmpBackdropPath;
            if (_cursor.isNull(_cursorIndexOfBackdropPath)) {
              _tmpBackdropPath = null;
            } else {
              _tmpBackdropPath = _cursor.getString(_cursorIndexOfBackdropPath);
            }
            final String _tmpGenreIds;
            if (_cursor.isNull(_cursorIndexOfGenreIds)) {
              _tmpGenreIds = null;
            } else {
              _tmpGenreIds = _cursor.getString(_cursorIndexOfGenreIds);
            }
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final String _tmpOriginalLanguage;
            if (_cursor.isNull(_cursorIndexOfOriginalLanguage)) {
              _tmpOriginalLanguage = null;
            } else {
              _tmpOriginalLanguage = _cursor.getString(_cursorIndexOfOriginalLanguage);
            }
            final String _tmpOriginalTitle;
            if (_cursor.isNull(_cursorIndexOfOriginalTitle)) {
              _tmpOriginalTitle = null;
            } else {
              _tmpOriginalTitle = _cursor.getString(_cursorIndexOfOriginalTitle);
            }
            final String _tmpOverview;
            if (_cursor.isNull(_cursorIndexOfOverview)) {
              _tmpOverview = null;
            } else {
              _tmpOverview = _cursor.getString(_cursorIndexOfOverview);
            }
            final double _tmpPopularity;
            _tmpPopularity = _cursor.getDouble(_cursorIndexOfPopularity);
            final String _tmpPosterPath;
            if (_cursor.isNull(_cursorIndexOfPosterPath)) {
              _tmpPosterPath = null;
            } else {
              _tmpPosterPath = _cursor.getString(_cursorIndexOfPosterPath);
            }
            final String _tmpReleaseDate;
            if (_cursor.isNull(_cursorIndexOfReleaseDate)) {
              _tmpReleaseDate = null;
            } else {
              _tmpReleaseDate = _cursor.getString(_cursorIndexOfReleaseDate);
            }
            final String _tmpTitle;
            if (_cursor.isNull(_cursorIndexOfTitle)) {
              _tmpTitle = null;
            } else {
              _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            }
            final boolean _tmpVideo;
            final int _tmp_1;
            _tmp_1 = _cursor.getInt(_cursorIndexOfVideo);
            _tmpVideo = _tmp_1 != 0;
            final double _tmpVoteAverage;
            _tmpVoteAverage = _cursor.getDouble(_cursorIndexOfVoteAverage);
            final int _tmpVoteCount;
            _tmpVoteCount = _cursor.getInt(_cursorIndexOfVoteCount);
            _result = new MovieEntity(_tmpPage,_tmpAdult,_tmpBackdropPath,_tmpGenreIds,_tmpId,_tmpOriginalLanguage,_tmpOriginalTitle,_tmpOverview,_tmpPopularity,_tmpPosterPath,_tmpReleaseDate,_tmpTitle,_tmpVideo,_tmpVoteAverage,_tmpVoteCount);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public PagingSource<Integer, MovieEntity> pagingSource() {
    final String _sql = "SELECT * FROM movieentity";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new LimitOffsetPagingSource<MovieEntity>(_statement, __db, "movieentity") {
      @Override
      @NonNull
      protected List<MovieEntity> convertRows(@NonNull final Cursor cursor) {
        final int _cursorIndexOfPage = CursorUtil.getColumnIndexOrThrow(cursor, "page");
        final int _cursorIndexOfAdult = CursorUtil.getColumnIndexOrThrow(cursor, "adult");
        final int _cursorIndexOfBackdropPath = CursorUtil.getColumnIndexOrThrow(cursor, "backdropPath");
        final int _cursorIndexOfGenreIds = CursorUtil.getColumnIndexOrThrow(cursor, "genreIds");
        final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(cursor, "id");
        final int _cursorIndexOfOriginalLanguage = CursorUtil.getColumnIndexOrThrow(cursor, "originalLanguage");
        final int _cursorIndexOfOriginalTitle = CursorUtil.getColumnIndexOrThrow(cursor, "originalTitle");
        final int _cursorIndexOfOverview = CursorUtil.getColumnIndexOrThrow(cursor, "overview");
        final int _cursorIndexOfPopularity = CursorUtil.getColumnIndexOrThrow(cursor, "popularity");
        final int _cursorIndexOfPosterPath = CursorUtil.getColumnIndexOrThrow(cursor, "posterPath");
        final int _cursorIndexOfReleaseDate = CursorUtil.getColumnIndexOrThrow(cursor, "releaseDate");
        final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(cursor, "title");
        final int _cursorIndexOfVideo = CursorUtil.getColumnIndexOrThrow(cursor, "video");
        final int _cursorIndexOfVoteAverage = CursorUtil.getColumnIndexOrThrow(cursor, "voteAverage");
        final int _cursorIndexOfVoteCount = CursorUtil.getColumnIndexOrThrow(cursor, "voteCount");
        final List<MovieEntity> _result = new ArrayList<MovieEntity>(cursor.getCount());
        while (cursor.moveToNext()) {
          final MovieEntity _item;
          final int _tmpPage;
          _tmpPage = cursor.getInt(_cursorIndexOfPage);
          final boolean _tmpAdult;
          final int _tmp;
          _tmp = cursor.getInt(_cursorIndexOfAdult);
          _tmpAdult = _tmp != 0;
          final String _tmpBackdropPath;
          if (cursor.isNull(_cursorIndexOfBackdropPath)) {
            _tmpBackdropPath = null;
          } else {
            _tmpBackdropPath = cursor.getString(_cursorIndexOfBackdropPath);
          }
          final String _tmpGenreIds;
          if (cursor.isNull(_cursorIndexOfGenreIds)) {
            _tmpGenreIds = null;
          } else {
            _tmpGenreIds = cursor.getString(_cursorIndexOfGenreIds);
          }
          final int _tmpId;
          _tmpId = cursor.getInt(_cursorIndexOfId);
          final String _tmpOriginalLanguage;
          if (cursor.isNull(_cursorIndexOfOriginalLanguage)) {
            _tmpOriginalLanguage = null;
          } else {
            _tmpOriginalLanguage = cursor.getString(_cursorIndexOfOriginalLanguage);
          }
          final String _tmpOriginalTitle;
          if (cursor.isNull(_cursorIndexOfOriginalTitle)) {
            _tmpOriginalTitle = null;
          } else {
            _tmpOriginalTitle = cursor.getString(_cursorIndexOfOriginalTitle);
          }
          final String _tmpOverview;
          if (cursor.isNull(_cursorIndexOfOverview)) {
            _tmpOverview = null;
          } else {
            _tmpOverview = cursor.getString(_cursorIndexOfOverview);
          }
          final double _tmpPopularity;
          _tmpPopularity = cursor.getDouble(_cursorIndexOfPopularity);
          final String _tmpPosterPath;
          if (cursor.isNull(_cursorIndexOfPosterPath)) {
            _tmpPosterPath = null;
          } else {
            _tmpPosterPath = cursor.getString(_cursorIndexOfPosterPath);
          }
          final String _tmpReleaseDate;
          if (cursor.isNull(_cursorIndexOfReleaseDate)) {
            _tmpReleaseDate = null;
          } else {
            _tmpReleaseDate = cursor.getString(_cursorIndexOfReleaseDate);
          }
          final String _tmpTitle;
          if (cursor.isNull(_cursorIndexOfTitle)) {
            _tmpTitle = null;
          } else {
            _tmpTitle = cursor.getString(_cursorIndexOfTitle);
          }
          final boolean _tmpVideo;
          final int _tmp_1;
          _tmp_1 = cursor.getInt(_cursorIndexOfVideo);
          _tmpVideo = _tmp_1 != 0;
          final double _tmpVoteAverage;
          _tmpVoteAverage = cursor.getDouble(_cursorIndexOfVoteAverage);
          final int _tmpVoteCount;
          _tmpVoteCount = cursor.getInt(_cursorIndexOfVoteCount);
          _item = new MovieEntity(_tmpPage,_tmpAdult,_tmpBackdropPath,_tmpGenreIds,_tmpId,_tmpOriginalLanguage,_tmpOriginalTitle,_tmpOverview,_tmpPopularity,_tmpPosterPath,_tmpReleaseDate,_tmpTitle,_tmpVideo,_tmpVoteAverage,_tmpVoteCount);
          _result.add(_item);
        }
        return _result;
      }
    };
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
