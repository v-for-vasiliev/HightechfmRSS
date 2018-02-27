package ru.vasiliev.hightechfmrss.presentation.main.rss;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.vasiliev.hightechfmrss.domain.model.RssFeed;

/**
 * Created by vasiliev on 11/02/2018.
 */

public interface RssView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showFeed(RssFeed feed);

    @StateStrategyType(SkipStrategy.class)
    void showLoader();

    @StateStrategyType(SkipStrategy.class)
    void showError(String error);
}
