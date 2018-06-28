package ru.vasiliev.hightechfmrss.presentation.article;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

import ru.vasiliev.hightechfmrss.domain.model.Article;

/**
 * Created by vasiliev on 11/02/2018.
 */

@StateStrategyType(AddToEndSingleStrategy.class)
public interface ArticleView extends MvpView {

    void showArticle(Article article);

    void updateMenu(boolean isBookmarked);

    @StateStrategyType(SkipStrategy.class)
    void enableMenuItem(int id);

    @StateStrategyType(SkipStrategy.class)
    void disableMenuItem(int id);
}
