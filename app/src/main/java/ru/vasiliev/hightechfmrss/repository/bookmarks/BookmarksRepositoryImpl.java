package ru.vasiliev.hightechfmrss.repository.bookmarks;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import ru.vasiliev.hightechfmrss.data.db.AppDatabase;
import ru.vasiliev.hightechfmrss.domain.model.Article;
import timber.log.Timber;

/**
 * Created by vasiliev on 11/02/2018.
 */

public class BookmarksRepositoryImpl implements BookmarksRepository {

    private AppDatabase mAppDatabase;

    public BookmarksRepositoryImpl(AppDatabase appDatabase) {
        Timber.d("BookmarksRepositoryImpl(%d)", hashCode());
        mAppDatabase = appDatabase;
    }

    @Override
    public Maybe<List<Article>> loadAllBookmarks() {
        Timber.d("BookmarksRepositoryImpl(%d) - loadAllBookmarks()", hashCode());
        return mAppDatabase.articleDao().getAll();
    }

    @Override
    public Maybe<Article> findByLink(String link) {
        Timber.d("BookmarksRepositoryImpl(%d) - findByLink(%s)", hashCode(), link);
        return mAppDatabase.articleDao().findByLink(link);
    }

    @Override
    public Completable addBookmark(Article article) {
        return Completable.fromAction(() -> {
            mAppDatabase.articleDao().insertAll(article);
            Timber.d("BookmarksRepositoryImpl(%d) - addBookmark(link: %s)", hashCode(),
                    article.link);
        });
    }

    @Override
    public Completable deleteBookmark(Article article) {
        return Completable.fromAction(() -> {
            mAppDatabase.articleDao().delete(article);
            Timber.d("BookmarksRepositoryImpl(%d) - deleteBookmark(%s)", hashCode(), article.link);
        });
    }

    @Override
    public Completable deleteAllBookmarks() {
        return Completable.fromAction(() -> {
            mAppDatabase.articleDao().deleteAll();
            Timber.d("BookmarksRepositoryImpl(%d) - deleteAllBookmarks()", hashCode());
        });
    }
}
