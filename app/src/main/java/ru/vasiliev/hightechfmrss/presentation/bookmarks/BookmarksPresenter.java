package ru.vasiliev.hightechfmrss.presentation.bookmarks;

import com.arellomobile.mvp.InjectViewState;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import ru.vasiliev.hightechfmrss.App;
import ru.vasiliev.hightechfmrss.di.bookmarks.BookmarksComponent;
import ru.vasiliev.hightechfmrss.domain.bookmarks.BookmarksInteractor;
import ru.vasiliev.hightechfmrss.domain.model.Article;
import ru.vasiliev.hightechfmrss.presentation.MvpBasePresenter;
import ru.vasiliev.hightechfmrss.presentation.Router;
import timber.log.Timber;

/**
 * Created by vasiliev on 01/04/2018.
 */

@InjectViewState
public class BookmarksPresenter extends MvpBasePresenter<BookmarksView> {

    @Inject
    BookmarksInteractor mBookmarksInteractor;

    @Inject
    Router mRouter;

    BookmarksPresenter() {
        BookmarksComponent component = App.getAppComponent().plusBookmarksComponent();
        component.inject(this);
    }

    void loadAllBookmarks() {
        getViewState().showLoader();

        mBookmarksInteractor.loadAllBookmarks()
                .subscribe(new DisposableMaybeObserver<List<Article>>() {
                    @Override
                    public void onSuccess(List<Article> articleList) {
                        getViewState().showBookmarks(articleList);
                        Timber.d("Bookmarks found");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Timber.e(throwable, "");
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("Bookmarks not found");
                    }
                });
    }

    void deleteAllBookmarks() {
        mBookmarksInteractor.deleteAllBookmarks().subscribe(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                Timber.d("Bookmarks deleted");
            }

            @Override
            public void onError(Throwable e) {
                // Error dialog
                Timber.d("Error delete bookmarks");
            }
        });
    }

    Router getRouter() {
        return mRouter;
    }
}
