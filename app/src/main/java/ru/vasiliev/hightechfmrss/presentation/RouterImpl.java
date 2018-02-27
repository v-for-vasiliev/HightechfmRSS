package ru.vasiliev.hightechfmrss.presentation;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import ru.vasiliev.hightechfmrss.domain.model.Article;
import ru.vasiliev.hightechfmrss.presentation.article.ArticleActivity;

/**
 * Created by vasiliev on 15/02/2018.
 */

public class RouterImpl implements Router {

    public RouterImpl() {
    }

    @Override
    public void openArticle(@NonNull AppCompatActivity activity, Article article) {
        ArticleActivity.Builder builder = new ArticleActivity.Builder();
        builder.setArticle(article);
        builder.start(activity);
    }
}
