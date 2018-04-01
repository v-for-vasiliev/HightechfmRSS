package ru.vasiliev.hightechfmrss.domain.article;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import ru.vasiliev.hightechfmrss.di.scope.ArticleScope;
import ru.vasiliev.hightechfmrss.domain.model.Article;
import ru.vasiliev.hightechfmrss.repository.bookmarks.BookmarksRepository;
import ru.vasiliev.hightechfmrss.utils.RxUtils;

/**
 * Created by vasiliev on 15/02/2018.
 */

@ArticleScope
public class ArticleInteractor {

    private BookmarksRepository mBookmarksRepository;

    @Inject
    ArticleInteractor(BookmarksRepository bookmarksRepository) {
        mBookmarksRepository = bookmarksRepository;
    }

    public Maybe<Article> findByLink(String link) {
        return mBookmarksRepository.findByLink(link).compose(RxUtils.maybeIoScheduler());
    }

    public Completable addBookmark(Article article) {
        return mBookmarksRepository.addBookmark(article).compose(RxUtils.completableIoScheduler());
    }

    public Completable deleteBookmark(Article article) {
        return mBookmarksRepository.deleteBookmark(article)
                .compose(RxUtils.completableIoScheduler());
    }
}
