package ru.vasiliev.hightechfmrss.presentation;

import androidx.appcompat.app.AppCompatActivity;

import ru.vasiliev.hightechfmrss.domain.model.Article;

/**
 * Created by vasiliev on 15/02/2018.
 */

public interface Router {

    void openArticle(AppCompatActivity activity, Article article);
}
