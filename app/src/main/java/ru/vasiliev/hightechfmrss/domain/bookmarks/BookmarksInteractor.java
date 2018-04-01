package ru.vasiliev.hightechfmrss.domain.bookmarks;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import ru.vasiliev.hightechfmrss.di.scope.BookmarksScope;
import ru.vasiliev.hightechfmrss.domain.model.Article;
import ru.vasiliev.hightechfmrss.repository.bookmarks.BookmarksRepository;
import ru.vasiliev.hightechfmrss.utils.RxUtils;

/**
 * Created by vasiliev on 01/04/2018.
 */

@BookmarksScope
public class BookmarksInteractor {

    private BookmarksRepository mBookmarksRepository;

    @Inject
    BookmarksInteractor(BookmarksRepository bookmarksRepository) {
        mBookmarksRepository = bookmarksRepository;
    }

    public Maybe<List<Article>> loadAllBookmarks() {
        return mBookmarksRepository.loadAllBookmarks().compose(RxUtils.maybeIoScheduler());
    }

    public Completable deleteAllBookmarks() {
        return mBookmarksRepository.deleteAllBookmarks().compose(RxUtils.completableIoScheduler());
    }
}
