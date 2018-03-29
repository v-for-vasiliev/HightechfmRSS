package ru.vasiliev.hightechfmrss.repository.bookmarks;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;
import ru.vasiliev.hightechfmrss.domain.model.Article;

/**
 * Created by vasiliev on 11/02/2018.
 */

public interface BookmarksRepository {

    Single<List<Article>> loadBookmarks(boolean allowCache);

    Maybe<Article> findByLink(String link);

    Single<Void> saveBookmark(Article article);

    Single<Void> deleteBookmark(Article article);

    Single<Void> deleteAllBookmarks();
}
