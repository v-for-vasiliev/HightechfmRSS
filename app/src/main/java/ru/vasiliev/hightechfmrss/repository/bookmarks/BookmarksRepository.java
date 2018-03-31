package ru.vasiliev.hightechfmrss.repository.bookmarks;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import ru.vasiliev.hightechfmrss.domain.model.Article;

/**
 * Created by vasiliev on 11/02/2018.
 */

public interface BookmarksRepository {

    Maybe<List<Article>> loadAllBookmarks();

    Maybe<Article> findByLink(String link);

    Completable addBookmark(Article article);

    Completable deleteBookmark(Article article);

    Completable deleteAllBookmarks();
}
