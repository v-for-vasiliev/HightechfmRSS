package ru.vasiliev.hightechfmrss.presentation.rss;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import ru.vasiliev.hightechfmrss.App;
import ru.vasiliev.hightechfmrss.di.rss.RssComponent;
import ru.vasiliev.hightechfmrss.domain.rss.RssInteractor;

/**
 * Created by vasiliev on 11/02/2018.
 */

@InjectViewState
public class RssPresenter extends MvpPresenter<RssView> {

    @Inject
    RssInteractor mRssInteractor;

    public RssPresenter() {
        RssComponent component = App.getAppComponent().plusRssComponent();
        component.inject(this);
    }

    public void getFeed() {
        getViewState().showLoader();
        mRssInteractor.getFeed().subscribe(rssFeed ->
                        getViewState().showFeed(rssFeed),
                throwable -> getViewState().showError(throwable.getMessage()));
    }
}
