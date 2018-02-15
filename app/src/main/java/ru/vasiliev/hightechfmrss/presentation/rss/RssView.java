package ru.vasiliev.hightechfmrss.presentation.rss;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.vasiliev.hightechfmrss.domain.model.RssFeed;

/**
 * Created by vasiliev on 11/02/2018.
 */

public interface RssView extends MvpView {
    
    @StateStrategyType(OneExecutionStateStrategy.class)
    void showFeed(RssFeed feed);

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showLoader();

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showError(String error);
}
