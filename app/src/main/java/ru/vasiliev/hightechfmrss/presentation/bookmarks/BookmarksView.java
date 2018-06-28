package ru.vasiliev.hightechfmrss.presentation.bookmarks;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import java.util.List;

import ru.vasiliev.hightechfmrss.domain.model.Article;

/**
 * Created by vasiliev on 01/04/2018.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface BookmarksView extends MvpView {

    @StateStrategyType(SkipStrategy.class)
    void showLoader();

    void showBookmarks(List<Article> articleList);

    void showError(String error);
}
