package ru.vasiliev.hightechfmrss.presentation.home;

import ru.vasiliev.hightechfmrss.domain.model.Article;

/**
 * Created by vasiliev on 15/02/2018.
 */

public interface Router {

    void openRss();

    void openArticle(Article article);
}
