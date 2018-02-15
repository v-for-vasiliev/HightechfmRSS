package ru.vasiliev.hightechfmrss.presentation.article;

import android.os.Bundle;

import com.arellomobile.mvp.InjectViewState;

import javax.inject.Inject;

import ru.vasiliev.hightechfmrss.App;
import ru.vasiliev.hightechfmrss.di.article.ArticleComponent;
import ru.vasiliev.hightechfmrss.domain.article.ArticleInteractor;
import ru.vasiliev.hightechfmrss.domain.model.Article;
import ru.vasiliev.hightechfmrss.presentation.MvpBasePresenter;

/**
 * Created by vasiliev on 11/02/2018.
 */

@InjectViewState
public class ArticlePresenter extends MvpBasePresenter<ArticleView> {

    @Inject
    ArticleInteractor mArticleInteractor;

    private Article mArticle;

    ArticlePresenter() {
        ArticleComponent component = App.getAppComponent().plusArticleComponent();
        component.inject(this);
    }

    void extractArguments(Bundle arguments) {
        mArticle = (Article) arguments.getSerializable(ArticleFragment.ARG_ARTICLE);
    }

    void showArticle() {
        getViewState().showArticle(mArticle);
    }

    public void saveArticle() {
    }
}
