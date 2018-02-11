package ru.vasiliev.hightechfmrss.presentation.rss;

import com.arellomobile.mvp.MvpView;

import ru.vasiliev.hightechfmrss.domain.model.RssFeed;

/**
 * Created by vasiliev on 11/02/2018.
 */

public interface RssView extends MvpView {
    void showRss(RssFeed feed);

    void showLoader();

    void showError(String error);
}
