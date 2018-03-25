package ru.vasiliev.hightechfmrss.presentation.article;

import android.content.Intent;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.Observable;
import ru.vasiliev.hightechfmrss.App;
import ru.vasiliev.hightechfmrss.di.article.ArticleComponent;
import ru.vasiliev.hightechfmrss.domain.article.ArticleInteractor;
import ru.vasiliev.hightechfmrss.domain.model.Article;
import ru.vasiliev.hightechfmrss.presentation.MvpBasePresenter;
import ru.vasiliev.hightechfmrss.utils.RxUtils;
import timber.log.Timber;

/**
 * Created by vasiliev on 11/02/2018.
 */

@InjectViewState
public class ArticlePresenter extends MvpBasePresenter<ArticleView> {

    @Inject
    ArticleInteractor mArticleInteractor;

    private Article mArticle;

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

    public Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mArticle.link);
        return shareIntent;
    }

    public void checkIsBookmarked() {
        addSubscription(Observable.fromCallable(() -> {
            Thread.sleep(1000);
            return false;
        }).compose(RxUtils.observableIoScheduler()).subscribe(
                aBoolean -> getViewState().updateMenu(aBoolean), throwable -> {
                    getViewState().updateMenu(false);
                    Timber.e(throwable, "");
                }));
    }

    public void bookmarkArticle() {

    }

    public void removeFromBookmarks() {

    }
}
