package ru.vasiliev.hightechfmrss.presentation.article;

import com.arellomobile.mvp.MvpView;

import ru.vasiliev.hightechfmrss.model.Article;

/**
 * Created by vasiliev on 11/02/2018.
 */

public interface ArticleView extends MvpView {
    void showArticle(Article article);
}
