package ru.vasiliev.hightechfmrss.presentation.main.rss;

import com.arellomobile.mvp.InjectViewState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;
import ru.vasiliev.hightechfmrss.App;
import ru.vasiliev.hightechfmrss.di.rss.RssComponent;
import ru.vasiliev.hightechfmrss.domain.model.Article;
import ru.vasiliev.hightechfmrss.domain.model.ArticleCategory;
import ru.vasiliev.hightechfmrss.domain.model.RssFeed;
import ru.vasiliev.hightechfmrss.domain.rss.RssInteractor;
import ru.vasiliev.hightechfmrss.presentation.MvpBasePresenter;
import ru.vasiliev.hightechfmrss.presentation.Router;
import timber.log.Timber;

/**
 * Created by vasiliev on 11/02/2018.
 */

@InjectViewState
public class RssPresenter extends MvpBasePresenter<RssView> {

    @Inject
    RssInteractor mRssInteractor;

    @Inject
    Router mRouter;

    private ArticleCategory mArticleCategory = ArticleCategory.ALL;

    private boolean mFeedLoaded = false;

    private boolean mFirstLaunch = false;

    RssPresenter() {
        RssComponent component = App.getAppComponent().plusRssComponent();
        component.inject(this);
    }

    void setArticleCategory(ArticleCategory category) {
        mArticleCategory = category;
    }

    ArticleCategory getArticleCategory() {
        return mArticleCategory;
    }

    @Override
    public void attachView(RssView view) {
        super.attachView(view);
        Timber.d("Presenter(%s) - attachView(mFirstLaunch = %b, isLoading = %b)", mArticleCategory,
                mFirstLaunch, mRssInteractor.isLoading());
        if (!mFirstLaunch && mRssInteractor.isLoading()) {
            getViewState().showLoader(mFeedLoaded);
        }
        mFirstLaunch = false;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        Timber.d("Presenter(%s) - onFirstViewAttach(isLoading = %b)", mArticleCategory,
                mRssInteractor.isLoading());
        mFirstLaunch = true;
        if (!mRssInteractor.isLoading()) {
            getFeed(true);
        } else {
            subscribeToUpdates();
        }
    }

    private void handleResult(RssFeed rssFeed) {
        Timber.d("Presenter(%s) - handleResult()", mArticleCategory);
        mFeedLoaded = true;
        if (mArticleCategory == ArticleCategory.ALL) {
            getViewState().showFeed(rssFeed.articleList);
        } else {
            List<Article> filteredList = new ArrayList<>();
            for (Article article : rssFeed.articleList) {
                if (ArticleCategory.of(article.category) == mArticleCategory) {
                    filteredList.add(article);
                }
            }
            getViewState().showFeed(filteredList);
        }
    }

    private void handleError(Throwable t) {
        Timber.e("Presenter(%s) - handleError(%s)", mArticleCategory, t.getMessage());
        getViewState().showError(t.getMessage());
    }

    void getFeed(boolean allowCache) {
        Timber.d("Presenter(%s) - getFeed(feedLoaded = %b)", mArticleCategory, mFeedLoaded);

        getViewState().showLoader(mFeedLoaded);

        addSubscription(mRssInteractor.loadFeed(allowCache).subscribeWith(
                new DisposableSingleObserver<RssFeed>() {

                    @Override
                    public void onSuccess(RssFeed rssFeed) {
                        handleResult(rssFeed);
                        subscribeToUpdates();
                    }

                    @Override
                    public void onError(Throwable t) {
                        handleError(t);
                        subscribeToUpdates();
                    }
                }));
    }

    private void subscribeToUpdates() {
        Timber.d("Presenter(%s) - subscribeToUpdates()", mArticleCategory);
        addSubscription(
                mRssInteractor.getUpdates().subscribe(this::handleResult, this::handleError));
    }

    boolean isLoading() {
        return mRssInteractor.isLoading();
    }

    boolean isFeedLoaded() {
        return mFeedLoaded;
    }

    Router getRouter() {
        return mRouter;
    }
}
