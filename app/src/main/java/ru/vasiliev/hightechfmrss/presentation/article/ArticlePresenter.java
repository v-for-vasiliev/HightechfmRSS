package ru.vasiliev.hightechfmrss.presentation.article;

import com.arellomobile.mvp.InjectViewState;

import android.content.Intent;

import javax.inject.Inject;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import ru.vasiliev.hightechfmrss.App;
import ru.vasiliev.hightechfmrss.R;
import ru.vasiliev.hightechfmrss.di.article.ArticleComponent;
import ru.vasiliev.hightechfmrss.domain.article.ArticleInteractor;
import ru.vasiliev.hightechfmrss.domain.model.Article;
import ru.vasiliev.hightechfmrss.presentation.base.MvpBasePresenter;
import timber.log.Timber;

/**
 * Created by vasiliev on 11/02/2018.
 */

@InjectViewState
public class ArticlePresenter extends MvpBasePresenter<ArticleView> {

    @Inject
    ArticleInteractor mArticleInteractor;

    private Article mArticle;

    private boolean mBookmarked = false;

    ArticlePresenter() {
        ArticleComponent component = App.getAppComponent().plusArticleComponent();
        component.inject(this);
    }

    void extractArguments(Intent startIntent) {
        if (startIntent != null) {
            mArticle = (Article) startIntent.getSerializableExtra(ArticleActivity.KEY_ARTICLE);
        }
    }

    void loadArticle() {
        getViewState().showArticle(mArticle);
    }

    Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mArticle.link);
        return shareIntent;
    }

    boolean isBookmarked() {
        return mBookmarked;
    }

    void checkIsBookmarked() {
        mArticleInteractor.findByLink(mArticle.link)
                .subscribe(new DisposableMaybeObserver<Article>() {
                    @Override
                    public void onSuccess(Article article) {
                        getViewState().updateMenu(mBookmarked = true);
                        Timber.d("Bookmark found");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        getViewState().updateMenu(mBookmarked = false);
                        Timber.e(throwable, "");
                    }

                    @Override
                    public void onComplete() {
                        getViewState().updateMenu(mBookmarked = false);
                        Timber.d("Bookmark not found");
                    }
                });
    }

    void bookmarkArticle() {
        mArticleInteractor.addBookmark(mArticle).subscribe(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                getViewState().updateMenu(mBookmarked = true);
                getViewState().enableMenuItem(R.id.menu_bookmark);
                Timber.d("Bookmark added");
            }

            @Override
            public void onError(Throwable e) {
                // Error dialog
                getViewState().enableMenuItem(R.id.menu_bookmark);
                Timber.d("Error add bookmark");
            }
        });
    }

    void removeFromBookmarks() {
        mArticleInteractor.deleteBookmark(mArticle).subscribe(new DisposableCompletableObserver() {
            @Override
            public void onComplete() {
                getViewState().updateMenu(mBookmarked = false);
                getViewState().enableMenuItem(R.id.menu_bookmark);
                Timber.d("Bookmark removed");
            }

            @Override
            public void onError(Throwable e) {
                // Error dialog
                getViewState().enableMenuItem(R.id.menu_bookmark);
                Timber.d("Error remove bookmark");
            }
        });
    }
}
