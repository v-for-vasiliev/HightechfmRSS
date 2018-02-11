package ru.vasiliev.hightechfmrss.presentation.rss;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.vasiliev.hightechfmrss.App;
import ru.vasiliev.hightechfmrss.di.component.RssComponent;
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
        mRssInteractor.getFeed().subscribeOn(Schedulers.io()).observeOn(
                AndroidSchedulers.mainThread()).subscribe(rssFeed -> {
            getViewState().showRss(rssFeed);
        }, throwable -> getViewState().showError(throwable.getMessage()));
    }
}
