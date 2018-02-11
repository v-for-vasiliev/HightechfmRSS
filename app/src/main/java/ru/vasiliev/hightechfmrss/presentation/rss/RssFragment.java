package ru.vasiliev.hightechfmrss.presentation.rss;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;

import ru.vasiliev.hightechfmrss.domain.model.RssFeed;

/**
 * Created by vasiliev on 11/02/2018.
 */

public class RssFragment extends MvpAppCompatFragment implements RssView {

    @InjectPresenter
    RssPresenter mRssPresenter;

    @Override
    public void showRss(RssFeed feed) {

    }

    @Override
    public void showLoader() {

    }

    @Override
    public void showError(String error) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRssPresenter.getFeed();
    }
}
