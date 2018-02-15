package ru.vasiliev.hightechfmrss.presentation.article;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.vasiliev.hightechfmrss.domain.model.Article;

/**
 * Created by vasiliev on 11/02/2018.
 */

public interface ArticleView extends MvpView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void showArticle(Article article);
}
