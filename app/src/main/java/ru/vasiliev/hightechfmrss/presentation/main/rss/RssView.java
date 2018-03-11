package ru.vasiliev.hightechfmrss.presentation.main.rss;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.vasiliev.hightechfmrss.domain.model.Article;

/**
 * Created by vasiliev on 11/02/2018.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface RssView extends MvpView {

    void showFeed(List<Article> articleList);

    void showLoader(boolean feedLoaded);

    void showError(String error);
}
