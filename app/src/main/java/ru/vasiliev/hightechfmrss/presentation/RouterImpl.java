package ru.vasiliev.hightechfmrss.presentation;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import ru.vasiliev.hightechfmrss.R;
import ru.vasiliev.hightechfmrss.domain.model.Article;
import ru.vasiliev.hightechfmrss.presentation.article.ArticleFragment;
import ru.vasiliev.hightechfmrss.presentation.rss.RssFragment;
import ru.vasiliev.hightechfmrss.utils.FragmentUtils;

/**
 * Created by vasiliev on 15/02/2018.
 */

public class RouterImpl implements Router {

    public RouterImpl() {
    }

    @Override
    public void openRss(@NonNull AppCompatActivity activity) {
        FragmentUtils.replaceWithHistory(activity.getSupportFragmentManager(),
                RssFragment.newInstance(),
                R.id.fragment_container);
    }

    @Override
    public void openArticle(@NonNull AppCompatActivity activity, Article article) {
        FragmentUtils.replaceWithHistory(activity.getSupportFragmentManager(),
                ArticleFragment.newInstance(article),
                R.id.fragment_container);
    }
}
