package ru.vasiliev.hightechfmrss.presentation.main.rss;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import io.reactivex.observers.DisposableSingleObserver;
import ru.vasiliev.hightechfmrss.App;
import ru.vasiliev.hightechfmrss.di.rss.RssComponent;
import ru.vasiliev.hightechfmrss.domain.model.RssFeed;
import ru.vasiliev.hightechfmrss.domain.rss.RssInteractor;
import ru.vasiliev.hightechfmrss.presentation.MvpBasePresenter;
import ru.vasiliev.hightechfmrss.presentation.Router;

/**
 * Created by vasiliev on 11/02/2018.
 */

@InjectViewState
public class RssPresenter extends MvpBasePresenter<RssView> {

    @Inject
    RssInteractor mRssInteractor;

    @Inject
    Router mRouter;

    RssPresenter() {
        RssComponent component = App.getAppComponent().plusRssComponent();
        component.inject(this);
    }

    void getFeed() {
        getViewState().showLoader();
        addSubscription(mRssInteractor.getFeed(false).subscribeWith(
                new DisposableSingleObserver<RssFeed>() {

                    @Override
                    public void onSuccess(RssFeed rssFeed) {
                        getViewState().showFeed(rssFeed);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewState().showError(e.getMessage());
                    }
                }));
    }

    Router getRouter() {
        return mRouter;
    }
}
