package ru.vasiliev.hightechfmrss.presentation.article;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.vasiliev.hightechfmrss.domain.model.Article;

/**
 * Created by vasiliev on 11/02/2018.
 */

public interface ArticleView extends MvpView {

    @StateStrategyType(AddToEndSingleStrategy.class)
    void showArticle(Article article);

    @StateStrategyType(AddToEndSingleStrategy.class)
    void updateMenu(boolean isBookmarked);
}
