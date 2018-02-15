package ru.vasiliev.hightechfmrss.presentation;

import android.support.v7.app.AppCompatActivity;

import ru.vasiliev.hightechfmrss.domain.model.Article;

/**
 * Created by vasiliev on 15/02/2018.
 */

public interface Router {

    void openRss(AppCompatActivity activity);

    void openArticle(AppCompatActivity activity, Article article);
}
